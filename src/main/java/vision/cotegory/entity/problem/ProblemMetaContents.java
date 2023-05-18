package vision.cotegory.entity.problem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vision.cotegory.entity.Origin;

import javax.persistence.*;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProblemMetaContents {
    @Column(name = "problem_number")
    private Integer problemNumber;

    @Column(name = "origin")
    @Enumerated(EnumType.STRING)
    private Origin origin;

    private String title;

    private String url;
}
