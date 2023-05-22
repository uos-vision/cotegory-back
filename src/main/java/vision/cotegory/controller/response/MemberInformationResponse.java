package vision.cotegory.controller.response;

import lombok.Data;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Role;
import vision.cotegory.entity.tag.Tag;
import vision.cotegory.entity.tag.TagGroup;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class MemberInformationResponse {
    private Long memberId;
    private String baekjoonHandle;
    private String imgUrl;
    private String nickName;
    private Set<Role> roles;

    private List<MemberTagGroupInformationResponse> memberTagGroupInformationResponses;
    private Map<Tag, Double> correctRate;

    public MemberInformationResponse(Member member) {
        this.memberId = member.getId();
        this.baekjoonHandle = member.getBaekjoonHandle();
        this.imgUrl = member.getImgUrl();
        this.nickName = member.getNickName();
        this.roles = member.getRoles();

        this.memberTagGroupInformationResponses = member.getMmr().entrySet().stream().map(entry -> {
            TagGroup tagGroup = entry.getKey();
            Integer mmr = entry.getValue();
            return new MemberTagGroupInformationResponse(tagGroup, mmr);
        }).collect(Collectors.toList());
        this.correctRate = member.getCorrectRate();
    }

    @Data
    static class MemberTagGroupInformationResponse {
        private Long tagGroupId;
        private String tagGroupName;
        private Integer mmr;

        public MemberTagGroupInformationResponse(TagGroup tagGroup, Integer mmr) {
            this.tagGroupId = tagGroup.getId();
            this.tagGroupName = tagGroup.getName();
            this.mmr = mmr;
        }
    }
}
