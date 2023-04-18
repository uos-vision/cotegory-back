package vision.cotegory.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TagGroup {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Tag> tags;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tagGroup")
    @Builder.Default
    private List<Quiz> quizzes = new ArrayList<>();

    @Override
    public String toString() {
        return name;
    }
}
