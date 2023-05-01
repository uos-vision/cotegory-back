package vision.cotegory.crawler.baekjoon;

import lombok.Data;

import java.util.List;

@Data
public class SolvedAcProblemDto {
    private Integer problemId;
    private String titleKo;
    private Integer level;
    private List<SolvedAcTagDto> tags;
    private List<SolvedAcTitleDto> titles;

    @Data
    static public class SolvedAcTagDto {
        private String key;
        private Integer bojTagId;
        private Integer problemCount;
        private Boolean isMeta;
    }

    @Data
    static public class SolvedAcTitleDto {
        private String language;
        private String languageDisplayName;
        private String title;
        private Boolean isOriginal;
    }
}
