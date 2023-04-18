package vision.cotegory.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vision.cotegory.entity.info.BaekjoonProblemInfo;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Quiz {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private BaekjoonProblemInfo baekjoonProblemInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    private TagGroup tagGroup;

    @Enumerated(EnumType.STRING)
    private Tag answerTag;

    private Integer mmr;

    private Boolean isActivated;

    @Builder
    public Quiz(BaekjoonProblemInfo baekjoonProblemInfo, TagGroup tagGroup, Tag answerTag, Integer mmr, Boolean isActivated) {
        this.baekjoonProblemInfo = baekjoonProblemInfo;
        this.tagGroup = tagGroup;
        this.answerTag = answerTag;
        this.mmr = mmr;
        this.isActivated = isActivated;
        baekjoonProblemInfo.getQuizzes().add(this);
        tagGroup.getQuizzes().add(this);
    }
}
