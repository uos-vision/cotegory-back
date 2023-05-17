package vision.cotegory.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AbnormalQuiz {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Quiz quiz;
}
