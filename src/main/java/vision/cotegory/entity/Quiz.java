package vision.cotegory.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vision.cotegory.entity.problem.Problem;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Quiz {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Problem problem;

    @ManyToOne(fetch = FetchType.LAZY)
    private TagGroup tagGroup;

    @Enumerated(EnumType.STRING)
    private Tag answerTag;

    private Integer mmr;

    private Boolean activated;

    @Builder
    public Quiz(Problem problem, TagGroup tagGroup, Tag answerTag, Integer mmr, Boolean activated) {
        this.problem = problem;
        this.tagGroup = tagGroup;
        this.answerTag = answerTag;
        this.mmr = mmr;
        this.activated = activated;
        problem.getQuizzes().add(this);
        tagGroup.getQuizzes().add(this);
    }
}
