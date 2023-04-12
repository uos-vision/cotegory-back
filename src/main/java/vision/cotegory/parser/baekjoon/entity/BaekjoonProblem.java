package vision.cotegory.parser.baekjoon.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vision.cotegory.entity.Tag;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BaekjoonProblem {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private Integer problemNumber;

    private String title;
    private Integer timeLimit;
    private Integer memoryLimit;
    private Integer subMissionCount;
    private Integer correctAnswerCount;
    private Integer correctUserCount;
    private Double correctRate;

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
}
