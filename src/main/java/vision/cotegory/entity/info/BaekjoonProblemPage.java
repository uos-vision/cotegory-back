package vision.cotegory.entity.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BaekjoonProblemPage{
    @Id
    @GeneratedValue
    private Long id;

    private Integer problemNumber;

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
