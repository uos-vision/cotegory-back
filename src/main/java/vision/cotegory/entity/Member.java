package vision.cotegory.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    private String loginId;
    private String pw;

    private String baekjoonHandle;
    private String nickName;

    private String imgUrl;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Sheet> sheets;
}
