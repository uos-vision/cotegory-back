package vision.cotegory.problemloader.baekjoon.dto;

import lombok.Data;

@Data
public class BaekjoonProblemMetaDto {
    private Integer problemNumber;
    private String title;

    public BaekjoonProblemMetaDto(Integer problemNumber, String title) {
        this.problemNumber = problemNumber;
        this.title = title;
    }
}
