package vision.cotegory.entity.problem;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vision.cotegory.entity.Origin;
import vision.cotegory.entity.Quiz;
import vision.cotegory.entity.Tag;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@SuperBuilder
@NoArgsConstructor
public abstract class Problem {
    @Id
    @GeneratedValue
    @Getter
    private Long id;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @Getter
    private Set<Tag> tags;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "problem")
    @Builder.Default
    @Getter
    private List<Quiz> quizzes = new ArrayList<>();

    @Getter
    @Setter
    private Integer mmr;

    abstract String getTitle();
    abstract Integer getProblemNumber();
    abstract Integer getTimeLimit();
    abstract Integer getMemoryLimit();

    abstract String getProblemBody();
    abstract String getProblemInput();
    abstract String getProblemOutput();

    abstract String getSampleInput();
    abstract String getSampleOutput();
    abstract Origin getProblemOrigin();
}
