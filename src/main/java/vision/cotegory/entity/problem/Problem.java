package vision.cotegory.entity.problem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vision.cotegory.entity.Quiz;
import vision.cotegory.entity.Tag;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
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
    private List<Quiz> quizzes = new ArrayList<>();

    @Embedded
    ProblemContents problemContents;

    @Builder
    public Problem(ProblemMeta problemMeta, Set<Tag> tags, ProblemContents problemContents) {
        this.problemMeta = problemMeta;
        this.tags = tags;
        this.problemContents = problemContents;
    }
}
