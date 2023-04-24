package vision.cotegory.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Recommend {
    @Id
    @GeneratedValue
    private Long id;

    private Integer recommendProblemNumber;

    @Enumerated(EnumType.STRING)
    private Origin origin;

    private LocalDateTime createTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @PrePersist
    void prePersist(){
        this.createTime = LocalDateTime.now();
    }
}
