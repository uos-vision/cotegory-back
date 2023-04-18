package vision.cotegory.entity.info;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
@SuperBuilder
public abstract class ProblemPage {
    @Id
    @GeneratedValue
    private Long id;


}
