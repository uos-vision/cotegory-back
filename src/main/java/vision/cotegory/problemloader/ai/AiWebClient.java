package vision.cotegory.problemloader.ai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import vision.cotegory.exception.exception.AiApiException;

import java.time.Duration;
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
                        return Mono.error(new AiApiException("접근 불가능한 엔티티입니다"));
                    return Mono.error(new AiApiException());
                })
                .bodyToFlux(Integer.class)
                .retryWhen(Retry.fixedDelay(3, Duration.ofMillis(200))
                        .filter(throwable -> throwable instanceof AiApiException)
                        .doBeforeRetry(before -> log.info("[aiConnect] retry"))
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                            throw new AiApiException(retrySignal.toString());
                        }))
                .collectList()
                .block();
    }
}

