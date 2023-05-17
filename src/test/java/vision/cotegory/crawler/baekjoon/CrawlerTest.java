package vision.cotegory.crawler.baekjoon;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class CrawlerTest {

    @Autowired
    BaekjoonProblemService baekjoonProblemService;

    @Test
    void crawlTagParallelByTagTest() {
        baekjoonProblemService.updateAll();
    }
}