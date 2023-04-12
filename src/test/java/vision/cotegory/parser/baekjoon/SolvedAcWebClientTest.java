package vision.cotegory.parser.baekjoon;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vision.cotegory.parser.baekjoon.util.SolvedAcWebClient;

@SpringBootTest
@Slf4j
class SolvedAcWebClientTest {

    @Autowired
    SolvedAcWebClient solvedacWebClient;

    @Test
    void testWebCleint() {

        log.info("{}", solvedacWebClient.getTagsByProblemNumber(10844));
    }

}