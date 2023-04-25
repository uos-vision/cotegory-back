package vision.cotegory.crawler.baekjoon.dto;

import lombok.Data;

import java.util.List;

@Data
public class SolvedAcProblemDto {
    private Integer problemId;
    private String titleKo;
    private Integer level;
    private List<SolvedAcTagDto> tags;
    private List<SolvedAcTitleDto> titles;
}
