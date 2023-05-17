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
                .build();
        final Recommend recommend2 = Recommend.builder()
                .member(member)
                .createTime(LocalDateTime.now())
                .build();

        recommendRepository.saveAll(List.of(recommend1, recommend2));

        Recommend recommend = recommendRepository.findById(recommend1.getId()).get();
        log.info("createTime : {}", recommend.getCreateTime());
    }
}