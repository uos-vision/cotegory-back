package vision.cotegory.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @Setter
    private Boolean activated;

    @Getter
    @Setter
    private Integer mmr = 1200;

    @Builder
    public Quiz(Problem problem, Integer mmr, TagGroup tagGroup, Tag answerTag, Boolean activated) {
        this.problem = problem;
        this.tagGroup = tagGroup;
        this.answerTag = answerTag;
        this.activated = activated;
        this.mmr = mmr;
        problem.getQuizzes().add(this);
        tagGroup.getQuizzes().add(this);
    }

    @Getter
    private Long submitCount;

    public void increaseSubmitCount() {
        this.submitCount += 1;
    }

    public void decreaseSubmitCount() {
        this.submitCount += 1;
    }

    @Getter
    private Long correctCount;

    public void increaseCorrectCount() {
        this.submitCount += 1;
    }

    public void decreaseCorrectCount() {
        this.submitCount += 1;
    }
}
