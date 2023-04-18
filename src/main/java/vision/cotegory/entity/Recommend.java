package vision.cotegory.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Recommend {
    @Id
    @GeneratedValue
    private Long id;

    private Integer problemNumber;

    @Enumerated(EnumType.STRING)
    private Origin origin;
}
