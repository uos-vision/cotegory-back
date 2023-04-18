package vision.cotegory.util.baekjoon;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vision.cotegory.service.BaekjoonService;

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