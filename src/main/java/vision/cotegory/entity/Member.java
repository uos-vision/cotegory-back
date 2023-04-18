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
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    @ElementCollection(fetch = FetchType.LAZY)
    @MapKeyJoinColumn(name = "tag_group_id")
    private Map<TagGroup, Integer> mmr;

    private String loginId;

    private String pw;

    private String baekjoonHandle;

    private String imgUrl;

    private String nickName;

    private Boolean isActivated;
}
