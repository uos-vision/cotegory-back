package vision.cotegory.initializer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Role;
import vision.cotegory.entity.tag.TagGroup;
import vision.cotegory.service.TagGroupService;
import vision.cotegory.repository.MemberRepository;
import vision.cotegory.repository.TagGroupRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static vision.cotegory.entity.tag.Tag.*;
import static vision.cotegory.entity.tag.Tag.UNION_FIND;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class Initializer {

    private final MemberRepository memberRepository;
    private final TagGroupService tagGroupService;
    private final PasswordEncoder passwordEncoder;
    private final TagGroupRepository tagGroupRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void memberInit() {
        makeUser();
        makeAdmin();
        saveTagGroup();
    }
    private void makeUser() {
        if(memberRepository.findByLoginIdAndActivatedIsTrue("member").isPresent())
            return;
        Member member = Member.builder()
                .loginId("member")
                .pw(passwordEncoder.encode("1111"))
                .roles(Set.of(Role.ROLE_USER))
                .baekjoonHandle("tori1753")
                .activated(true)
                .mmr(Map.of(
                        tagGroupService.getTagGroupConsts().get(0), 200,
                        tagGroupService.getTagGroupConsts().get(1), 300,
                        tagGroupService.getTagGroupConsts().get(2), 400))
                .nickName("UOS닉네임")
                .build();

        memberRepository.save(member);
        log.info("[memberInit]{}", member);
    }

    private void makeAdmin() {
        if(memberRepository.findByLoginIdAndActivatedIsTrue("admin").isPresent())
            return;
        Member admin = Member.builder()
                .loginId("admin")
                .pw(passwordEncoder.encode("1111"))
                .roles(Set.of(Role.ROLE_ADMIN))
                .activated(true)
                .nickName("admin닉네임")
                .build();

        memberRepository.save(admin);
        log.info("[adminInit]{}", admin);
    }

    private void saveTagGroup() {
        List<TagGroup> tagGroups = new ArrayList<>();

        final TagGroup groupA = TagGroup.builder().name("groupA").tags(Set.of(
                GREEDY,
                DP,
                BRUTE_FORCE,
                BINARY_SEARCH
        )).build();
        tagGroups.add(groupA);

        final TagGroup groupB = TagGroup.builder().name("groupB").tags(Set.of(
                DFS,
                BFS,
                BRUTE_FORCE,
                DP
        )).build();
        tagGroups.add(groupB);

        final TagGroup groupC = TagGroup.builder().name("groupC").tags(Set.of(
                DIJKSTRA,
                FLOYD_WARSHALL,
                BIT_MASKING,
                UNION_FIND
        )).build();
        tagGroups.add(groupC);

        for(var tagGroup : tagGroups){
            if(tagGroupRepository.findByName(tagGroup.getName()).isPresent())
                continue;
            tagGroupRepository.save(tagGroup);
            log.info("[tagGroupConstInit]{}", tagGroup.getName());
        }
    }

}
