package vision.cotegory.parser.baekjoon;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import vision.cotegory.entity.Tag;
import vision.cotegory.exception.exception.SolvedAPiException;
import vision.cotegory.parser.baekjoon.dto.solvedac.SolvedacProblemDto;
import vision.cotegory.parser.baekjoon.dto.solvedac.SolvedacProblemDtos;
import vision.cotegory.parser.baekjoon.dto.solvedac.SolvedacTag;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SolvedacWebClient {

    private final WebClient webClient;

    public Set<Tag> getTags(Integer problemNumber) {
        SolvedacProblemDtos solvedacProblemDtos = webClient.get()
                .uri(uri -> uri.path("/problem/lookup")
                        .queryParam("problemIds", problemNumber)
                        .build())
                .retrieve()
                .onStatus(HttpStatus::isError, response -> Mono.error(new SolvedAPiException()))
                .bodyToMono(SolvedacProblemDtos.class)
                .block();

        List<SolvedacTag> solvedacTags = solvedacProblemDtos.getSolvedacProblemDtos().get(0).getTags();

        return solvedacTags.stream()
                .map(e -> e.getBojTagIdDtos().get(0).getBojTagId())
                .map(Tag::of)
                .collect(Collectors.toSet());
    }
}
