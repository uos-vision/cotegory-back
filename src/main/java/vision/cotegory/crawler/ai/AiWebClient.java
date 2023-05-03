package vision.cotegory.crawler.ai;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import vision.cotegory.exception.exception.AIApiException;
import vision.cotegory.exception.exception.SolvedApiException;

import java.util.List;

@Component
public class AiWebClient {

    private final WebClient webClient;

    public AiWebClient(WebClient webClient) {
        this.webClient = webClient
                .mutate()
                .baseUrl("https://ai-server")
                .build();
    }

    public List<Integer> getRecommendProblemNumbers(AiRecommendProblemRequest aiRecommendProblemRequest){
        return webClient.post()
                .uri("/recommend")
                .bodyValue(aiRecommendProblemRequest)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> {
                    if (response.statusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY))
                        return Mono.error(new AIApiException("접근 불가능한 엔티티입니다"));
                    return Mono.error(new SolvedApiException());
                })
                .bodyToFlux(Integer.class)
                .collectList()
                .block();
    }
}

