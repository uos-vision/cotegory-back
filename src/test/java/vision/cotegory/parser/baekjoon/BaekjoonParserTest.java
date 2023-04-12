package vision.cotegory.parser.baekjoon;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vision.cotegory.entity.Tag;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BaekjoonParserTest {

    @Autowired
    BaekjoonParser baekjoonParser;

    @Test
    void baekjoonParserTest(){
        List<Integer> problemPagesByTag = baekjoonParser.getProblemPagesByTag(Tag.DP);
        for(var problemPage : problemPagesByTag){
            List<Integer> problemNumbers = baekjoonParser.getProblemNumbers(Tag.DP, problemPage);
            for(var problemNumber : problemNumbers)
                baekjoonParser.getBaekjoonProblemDtoWithoutTag(problemNumber);
        }
    }

}