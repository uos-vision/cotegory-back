package vision.cotegory.entity.problem;

import lombok.*;

import javax.persistence.*;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProblemContents {
    @Lob
    private String problemBody;
    @Lob
    private String problemInput;
    @Lob
    private String problemOutput;
    @Lob
    private String sampleInput;
    @Lob
    private String sampleOutput;
    private String title;
    private Integer timeLimit;
    private Integer memoryLimit;
    private Integer subMissionCount;
    private Integer correctAnswerCount;
    private Integer correctUserCount;
    private Double correctRate;
}
