package vision.cotegory.crawler.baekjoon.dto;

import lombok.Data;

@Data
public class SolvedAcTagDto {
    private String key;
    private Integer bojTagId;
    private Integer problemCount;
    private Boolean isMeta;
}
