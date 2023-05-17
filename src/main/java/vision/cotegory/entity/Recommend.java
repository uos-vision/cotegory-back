package vision.cotegory.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vision.cotegory.entity.problem.Problem;

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

    @ManyToOne(fetch = FetchType.LAZY)
    private Problem problem;

    private LocalDateTime createTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    public Recommend(RecommendType recommendType, Problem problem, LocalDateTime createTime, Member member) {
        this.recommendType = recommendType;
        this.problem = problem;
        this.createTime = createTime;
        this.member = member;
        member.getRecommends().put(recommendType,this);
    }

    @PrePersist
    void prePersist(){
        this.createTime = LocalDateTime.now();
    }
}
