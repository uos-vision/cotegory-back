package vision.cotegory.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.mapping.ToOne;
import org.springframework.beans.factory.parsing.Problem;
import vision.cotegory.parser.baekjoon.dto.BaekjoonProblemDto;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    @Id
    @GeneratedValue
    private Long id;



    @OneToOne(fetch = FetchType.LAZY)
    private BaekjoonProblemDto problem;
}
