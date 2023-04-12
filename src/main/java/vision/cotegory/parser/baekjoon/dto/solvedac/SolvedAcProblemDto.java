package vision.cotegory.parser.baekjoon.dto.solvedac;

import lombok.Data;

import java.util.List;

@Data
public class SolvedAcProblemDto {
    private Integer problemId;
    private String titleKo;
    private List<SolvedAcTagDto> tags;
    private List<SolvedAcTitleDto> titles;
}
