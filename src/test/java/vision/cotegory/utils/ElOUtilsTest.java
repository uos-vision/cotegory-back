package vision.cotegory.utils;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Quiz;
import vision.cotegory.entity.tag.TagGroup;
import vision.cotegory.entity.problem.Problem;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Slf4j
@Transactional
class ElOUtilsTest {

    @Autowired
    ElOUtils elo;

    @Test
    void updateCorrectRate() {
        elo.updateCorrectRate(0.2);
        Assertions.assertThat(elo.getCorrectRate()).isEqualTo(0.2);
    }

    @Test
    void updateElo() {

        TagGroup tagGroup = TagGroup.builder()
                .name("testTag")
                .build();

        Map<TagGroup, Integer> map = new HashMap<>();
        map.put(tagGroup, 300);

        Member member = Member.builder()
                .mmr(map)
                .build();

        Problem problem = Problem.builder()
               .build();

        Quiz quiz = Quiz.builder()
                .tagGroup(tagGroup)
                .problem(problem)
                .build();

//        elo.updateCorrectRate(0.2);
//        log.info("init : {}, {}", member.getMmr().get(tagGroup), quiz.getMmr());
//        Pair<Integer, Integer> pair1 = elo.updateElo(member, su);
//        log.info("return : {}, {}", pair1.getFirst(), pair1.getSecond());
//        elo.updateCorrectRate(0.2);
//        Pair<Integer, Integer> pair2 = elo.updateElo(member, quiz, true);
//        log.info("return : {}, {}", pair2.getFirst(), pair2.getSecond());
    }
}