package vision.cotegory.crawler.baekjoon;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vision.cotegory.entity.TagGroupConst;
import vision.cotegory.repository.BaekjoonProblemRepository;
import vision.cotegory.repository.ProblemRepository;
import vision.cotegory.repository.QuizRepository;

@SpringBootTest
@Slf4j
class CrawlerTest {

    @Autowired
    BaekjoonProblemService baekjoonProblemService;
    @Autowired
    ProblemRepository problemRepository;
    @Autowired
    QuizRepository quizRepository;
    @Autowired
    TagGroupConst tagGroupConst;
    @Autowired
    BaekjoonProblemRepository baekjoonProblemRepository;

    @Test
    void crawlTagParallelByTagTest() {
        baekjoonProblemService.updateAll();

        tagGroupConst.getTagGroupConsts().forEach(tagGroup -> tagGroup.getTags().forEach(answerTag -> {
            Long cnt = quizRepository.countAllByTagGroupAndAnswerTag(tagGroup, answerTag);
            log.info("그룹:{} | 정답태그:{} | {}개", tagGroup.getName(), answerTag.toKorean(), cnt);
        }));
    }
}