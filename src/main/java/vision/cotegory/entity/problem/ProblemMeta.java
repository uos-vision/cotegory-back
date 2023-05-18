package vision.cotegory.entity.problem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"problem_number", "origin"}))
public class ProblemMeta {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private ProblemMetaContents problemMetaContents;

    public ProblemMeta(ProblemMetaContents problemMetaContents) {
        this.problemMetaContents = problemMetaContents;
    }
}
