package vision.cotegory.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.tag.Tag;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SpringBootTest
@Slf4j
@Transactional
public class DPRandomTest {

    @Test
    public void dpRandomTest() {

        Map<Tag, Long> submission = new HashMap<>();
        for(Tag tag : Tag.values())
        {
            submission.put(tag, 10L);
        }

        Map<Tag, Long> correct = new HashMap<>();
        for(Tag tag : Tag.values())
        {
            correct.put(tag, 10L);
        }

        correct.put(Tag.BINARY_SEARCH, 9L);
        correct.put(Tag.DP, 8L);

        Member member = Member
                .builder()
                .submissionCount(submission)
                .correctCount(correct)
                .build();

        double correctSum = 0.0;
        double tmp = 0.0;
        Tag answerTag = Tag.DP;
        Map<Tag, Double> correctRate = member.getCorrectRate();
        Set<Tag> tags = correctRate.keySet();
        for (Tag tag : tags) {
            correctSum += 1 - correctRate.get(tag);
        }

        double randNum = Math.random() * correctSum;
        for (Tag tag : tags) {
            tmp += (1 - correctRate.get(tag));
            if (tmp > randNum)
            {
                answerTag = tag;
                break;
            }
        }
        System.out.println("answerTag = " + answerTag);
    }

    @Test
    void repeatTest() {
        for(int i = 0; i < 10; i++)
            this.dpRandomTest();
    }
}
