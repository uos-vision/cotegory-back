package vision.cotegory.problemloader.programmers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProgrammersCSVReaderTest {

    @Autowired
    ProgrammersCSVReader programmersCSVReader;

    @Test
    void test(){
        programmersCSVReader.readCSV();
    }
}