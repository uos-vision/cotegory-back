package vision.cotegory.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import vision.cotegory.entity.problem.ProblemMeta;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Recommend {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private RecommendType recommendType;

    //Problem -> ProblemMeta
    @ManyToOne(fetch = FetchType.LAZY)
    private ProblemMeta problemMeta;

    private LocalDateTime createTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    public Recommend(RecommendType recommendType, ProblemMeta problemMeta, Member member) {
        this.recommendType = recommendType;
        this.problemMeta = problemMeta;
        this.member = member;
        member.getRecommends().put(recommendType,this);
    }

    @PrePersist
    void prePersist(){
        this.createTime = LocalDateTime.now();
    }
}
