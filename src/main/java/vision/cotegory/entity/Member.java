package vision.cotegory.entity;


import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    @ElementCollection(fetch = FetchType.LAZY)
    @MapKeyJoinColumn(name = "tag_group_id")
    private Map<TagGroup, Integer> mmr;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    private String loginId;

    private String pw;

    private String baekjoonHandle;

    @Setter
    private String imgUrl;

    private String nickName;

    private Boolean activated;
}
