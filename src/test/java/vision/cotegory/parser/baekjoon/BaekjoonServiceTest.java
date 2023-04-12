package vision.cotegory.parser.baekjoon;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import vision.cotegory.entity.Tag;
import vision.cotegory.parser.baekjoon.util.BaekjoonProblemParser;

@SpringBootTest
@Slf4j
class BaekjoonServiceTest {

    @Autowired
    BaekjoonService baekjoonService;

    @Test
    void crawlTagParallelByTagTest() {
        baekjoonService.updateAll();
    }
}