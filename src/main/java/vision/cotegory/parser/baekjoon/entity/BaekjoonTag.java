package vision.cotegory.parser.baekjoon.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vision.cotegory.entity.Tag;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BaekjoonTag {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private Integer problemNumber;

    @ElementCollection
    private Set<Tag> tags;
}
