package vision.cotegory.parser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vision.cotegory.entity.Tag;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class BaekjoonDAOTest {

    @Autowired
    private BaekjoonDAO baekjoonDAO;

    @Test
    void baekjoonCrawlByPageNumberTest(){
        final ProblemDTO problemDTO = baekjoonDAO.getByProblemNumber(1007);
        log.info("{}", problemDTO);
    }

    @Test
    void crawlAllByTag(){
        baekjoonDAO.crawlAllByTag(Tag.DP);
    }
}