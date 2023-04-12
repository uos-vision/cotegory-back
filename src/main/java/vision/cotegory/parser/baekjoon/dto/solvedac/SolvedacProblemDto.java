package vision.cotegory.parser.baekjoon.dto.solvedac;

import lombok.Data;

import java.util.List;

@Data
public class SolvedacProblemDto {
    private Integer problemId;
    private String titleKo;
    private List<SolvedacTag> tags;

}
