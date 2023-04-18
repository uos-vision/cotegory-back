package vision.cotegory.entity.info;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import vision.cotegory.entity.Quiz;
import vision.cotegory.entity.Tag;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
public class BaekjoonProblemInfo{
    @Id
    @GeneratedValue
    private Long id;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private Set<Tag> tags;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "baekjoonProblemInfo")
    @Builder.Default
    private List<Quiz> quizzes = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "problemNumber")
    private BaekjoonProblemPage problemPage;

    @Column(unique = true)
    private Integer problemNumber;

    private Integer level;
}
