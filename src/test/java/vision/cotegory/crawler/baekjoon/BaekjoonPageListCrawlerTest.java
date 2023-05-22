package vision.cotegory.crawler.baekjoon;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import vision.cotegory.entity.tag.TagGroup;
import vision.cotegory.repository.TagGroupRepository;

import java.util.List;

@SpringBootTest
@Slf4j
@ActiveProfiles("prod")
class BaekjoonPageListCrawlerTest {

    @Autowired
    BaekjoonCrawler baekjoonCrawler;
    @Autowired
    TagGroupRepository tagGroupRepository;
    
    @Test
    void crawl(){
        baekjoonCrawler.crawlAll();
    }
}