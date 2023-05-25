package vision.cotegory.problemloader;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vision.cotegory.problemloader.baekjoon.BaekjoonCrawler;
import vision.cotegory.problemloader.programmers.ProgrammersCSVReader;

@SpringBootTest
public class ProblemLoaderTest {
    @Autowired
    ProgrammersCSVReader programmersCSVReader;
    @Autowired
    BaekjoonCrawler baekjoonCrawler;

    @Test
    void problemLoad(){
        baekjoonCrawler.crawlAll();
        programmersCSVReader.readCSV();
    }
}
