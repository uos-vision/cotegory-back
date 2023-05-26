package vision.cotegory.entity.problem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vision.cotegory.entity.Origin;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(indexes={
        @Index(columnList = "origin, problem_number", unique = true),
        @Index(columnList = "is_company, origin, problem_number")
})
public class ProblemMeta {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "problem_number")
    private Integer problemNumber;

    @Column(name = "origin")
    @Enumerated(EnumType.STRING)
    private Origin origin;

    private String title;

    private String url;

    @Column(name="is_company")
    @Builder.Default
    private boolean isCompany = false;
}
