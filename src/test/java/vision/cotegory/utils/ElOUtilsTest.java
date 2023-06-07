package vision.cotegory.utils;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.transaction.annotation.Transactional;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Quiz;
import vision.cotegory.entity.Submission;
import vision.cotegory.entity.tag.Tag;
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
        map.put(tagGroup, 1200);

        Member member = Member.builder()
                .mmr(map)
                .build();

        Problem problem = Problem.builder()
               .build();

        Quiz quiz = Quiz.builder()
                .tagGroup(tagGroup)
                .answerTag(Tag.DP)
                .problem(problem)
                .build();

        Submission submission = Submission
                .builder()
                        .quiz(quiz)
                .selectTag(Tag.DP)
                                .build();

        log.info("init : {}, {}", member.getMmr().get(tagGroup), quiz.getMmr());
        Pair<Integer, Integer> pair1;
        for (int i = 0; i < 10; i++) {
            pair1 = elo.updateElo(member, submission);
            member.getMmr().put(tagGroup, pair1.getFirst());
            quiz.setMmr(pair1.getSecond());
            log.info("return : {}, {}", pair1.getFirst(), pair1.getSecond());
        }
    }
}