package vision.cotegory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Role;
import vision.cotegory.entity.tag.TagGroup;
import vision.cotegory.exception.exception.DuplicatedEntityException;
import vision.cotegory.exception.exception.NotExistBaekjoonHandleException;
import vision.cotegory.repository.MemberRepository;
import vision.cotegory.service.dto.RegisterDto;
import vision.cotegory.utils.S3Utils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TagGroupService tagGroupService;
    private final WebClient webClient;
    private final S3Utils s3Utils;

    public Member register(RegisterDto registerDto) {

        Optional<Member> memberOptional = memberRepository
                .findByLoginIdAndActivatedIsTrue(registerDto.getLoginId());

        if (memberOptional.isPresent())
            throw new DuplicatedEntityException("로그인아이디가 중복됩니다");

        if (!isExistBaekjoonHandle(registerDto.getBaekjoonHandle()))
            throw new NotExistBaekjoonHandleException();


        Member member = Member.builder()
                .loginId(registerDto.getLoginId())
                .baekjoonHandle(registerDto.getBaekjoonHandle())
                .mmr(tagGroupService.getTagGroupConsts().stream().collect(Collectors.toMap(
                        v1 -> v1,
                        v1 -> 1200
                )))
                .pw(passwordEncoder.encode(registerDto.getPw()))
                .nickName(registerDto.getNickName())
                .roles(Set.of(Role.ROLE_USER))
                .activated(true)
                .build();

        return memberRepository.save(member);
    }

    public Boolean isExistBaekjoonHandle(String baekjoonHandle) {
        try {
            webClient.mutate()
                    .baseUrl("https://www.acmicpc.net")
                    .build()
                    .get()
                    .uri(String.format("/user/%s", baekjoonHandle))
                    .retrieve()
                    .toBodilessEntity()
                    .block()
                    .getStatusCode();
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public Boolean isDuplicatedLoginId(String loginId) {
        Optional<Member> memberOptional = memberRepository
                .findByLoginIdAndActivatedIsTrue(loginId);
        return memberOptional.isPresent();
    }

    public String uploadImage(Member member, MultipartFile multipartFile) {
        s3Utils.delete(member.getImgUrl());
        String uploadedUrl = s3Utils.upload(multipartFile);
        member.setImgUrl(uploadedUrl);

        return uploadedUrl;
    }

    public void deleteImage(Member member) {
        s3Utils.delete(member.getImgUrl());
        member.setImgUrl(null);
    }

    public Map<TagGroup, Integer> rank(Member member) {

        List<Member> memberList = memberRepository.findAll();
        Map<TagGroup, List<Integer>> allMemberMmr = new HashMap<>();
        Map<TagGroup, Integer> rankResult = new HashMap<>();
        List<TagGroup> allTagGroup = tagGroupService.getTagGroupConsts();
        allTagGroup.forEach(tagGroup -> allMemberMmr.put(tagGroup, new ArrayList<>()));
        memberList.forEach(m -> m.getMmr().forEach((key, value) -> allMemberMmr.get(key).add(value)));
        allTagGroup.forEach(
                tagGroup ->
                        rankResult.put(tagGroup,
                                Collections.binarySearch(allMemberMmr
                                                .get(tagGroup)
                                                .stream()
                                                .sorted()
                                                .collect(Collectors.toList()),
                                        member.getMmr().get(tagGroup)))
        );

        for (Map.Entry<TagGroup,Integer> entry : rankResult.entrySet()) {
            if (entry.getValue() < 0)
                entry.setValue(memberList.size() - entry.getValue() * -1 + 1);
            else
                entry.setValue(memberList.size() - entry.getValue());
        }

        return rankResult;
    }
}
