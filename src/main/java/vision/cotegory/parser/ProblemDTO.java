package vision.cotegory.parser;

import lombok.*;
import vision.cotegory.entity.Tag;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class ProblemDTO {
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
    private String problemInput;
    private String problemOutput;
    private String sampleInput;
    private String sampleOutput;
    @Lob
    private String problemBody;
    @ElementCollection
    private Set<Tag> tags;
}
