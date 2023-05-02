package vision.cotegory.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Origin;
import vision.cotegory.entity.Recommend;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SpringBootTest
@Slf4j
class RecommendRepositoryTest {

    @Autowired
    RecommendRepository recommendRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void recommendTest(){
        final Member member = Member.builder()
                .nickName("hello")
                .activated(true)
                .build();
        memberRepository.save(member);


        final Recommend recommend1 = Recommend.builder()
                .member(member)
                .createTime(LocalDateTime.now())
                .recommendProblemNumber(1)
                .origin(Origin.PROGRAMMERS)
                .build();
        final Recommend recommend2 = Recommend.builder()
                .member(member)
                .createTime(LocalDateTime.now().plus(10, ChronoUnit.SECONDS))
                .recommendProblemNumber(2)
                .origin(Origin.PROGRAMMERS)
                .build();

        recommendRepository.saveAll(List.of(recommend1, recommend2));

        Recommend recommend = recommendRepository.findTopByMemberAndOriginOrderByCreateTimeDesc(member, Origin.PROGRAMMERS).get();
        log.info("createTime : {}, number : {}", recommend.getCreateTime(), recommend.getRecommendProblemNumber());
    }
}