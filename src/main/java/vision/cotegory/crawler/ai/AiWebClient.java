package vision.cotegory.crawler.ai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import vision.cotegory.exception.exception.AIApiException;
import vision.cotegory.exception.exception.SolvedApiException;

import java.util.List;

@Component
@Slf4j
public class AiWebClient {

    private final WebClient webClient;

    public AiWebClient(WebClient webClient, @Value("${ai.server.url}") String aiServerUrl) {
        log.info("[ai-server-url] {}", aiServerUrl);
        this.webClient = webClient
                .mutate()
                .baseUrl(aiServerUrl)
                .build();
    }

    public List<Integer> getRecommendProblemNumbers(AiRecommendProblemRequest aiRecommendProblemRequest) {
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

