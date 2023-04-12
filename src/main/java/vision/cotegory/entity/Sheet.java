package vision.cotegory.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Sheet {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    Tag answerTag;

    LocalDateTime inTime;

    LocalDateTime outTime;

    @ManyToOne(fetch = FetchType.LAZY)
    Member member;
}
