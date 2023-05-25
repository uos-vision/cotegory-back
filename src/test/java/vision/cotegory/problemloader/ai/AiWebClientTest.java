package vision.cotegory.problemloader.ai;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import vision.cotegory.entity.tag.Tag;

import java.util.List;

@SpringBootTest
@ActiveProfiles("prod")
class AiWebClientTest {
    @Autowired
    AiWebClient aiWebClient;

    @Test
    void test() {
        AiRecommendProblemRequest aiRecommendProblemRequest = AiRecommendProblemRequest.builder()
                .cnt(10)
                .handle("tori1753")
                .tag(Tag.BFS.toKorean())
                .build();
        final List<Integer> recommendProblemNumbers = aiWebClient.getRecommendProblemNumbers(aiRecommendProblemRequest);
        for(var number : recommendProblemNumbers)
            System.out.println(number);

    }
}