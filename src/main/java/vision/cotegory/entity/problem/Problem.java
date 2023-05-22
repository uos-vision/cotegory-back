package vision.cotegory.entity.problem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vision.cotegory.entity.Quiz;
import vision.cotegory.entity.tag.Tag;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Problem {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    ProblemMeta problemMeta;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private Set<Tag> tags;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "problem")
    @Builder.Default
    private List<Quiz> quizzes = new ArrayList<>();

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
    private Integer timeLimit;
    private Integer memoryLimit;
}
