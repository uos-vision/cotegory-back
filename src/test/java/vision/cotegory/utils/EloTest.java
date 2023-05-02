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
import vision.cotegory.entity.TagGroup;
import vision.cotegory.entity.problem.BaekjoonProblem;
import vision.cotegory.entity.problem.Problem;
import vision.cotegory.repository.MemberRepository;
import vision.cotegory.repository.QuizRepository;
import vision.cotegory.repository.SubmissionRepository;
import vision.cotegory.service.QuizService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
class EloTest {

    @Autowired
    Elo elo;

    @Test
    void updateCorrectRate() {
        elo.updateCorrectRate(0.2);
        Assertions.assertThat(elo.getCorrectRate()).isEqualTo(0.2);
    }

    @Test
    void updateElo() {

        elo.updateCorrectRate(0.2);
        TagGroup tagGroup = TagGroup.builder()
                .name("testTag")
                .build();

        Map<TagGroup, Integer> map = new HashMap<>();
        map.put(tagGroup, 300);

        Member member = Member.builder()
                .mmr(map)
                .build();

        Problem problem = BaekjoonProblem.builder()
               .mmr(300)
               .build();

        Quiz quiz = Quiz.builder()
                .tagGroup(tagGroup)
                .problem(problem)
                .build();

        log.info("init : {}, {}", member.getMmr().get(tagGroup), quiz.getProblem().getMmr());
        Pair<Integer, Integer> pair = elo.updateElo(member, quiz, false);
        log.info("return : {}, {}", pair.getFirst(), pair.getSecond());
    }
}