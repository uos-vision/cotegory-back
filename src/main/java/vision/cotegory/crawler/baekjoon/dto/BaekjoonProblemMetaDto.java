package vision.cotegory.crawler.baekjoon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class BaekjoonProblemMetaDto {
    private Integer problemNumber;
    private String title;

    public BaekjoonProblemMetaDto(Integer problemNumber, String title) {
        this.problemNumber = problemNumber;
        this.title = title;
    }
}
