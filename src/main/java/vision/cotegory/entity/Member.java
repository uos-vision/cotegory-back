package vision.cotegory.entity;


import lombok.*;
import vision.cotegory.entity.tag.Tag;
import vision.cotegory.entity.tag.TagGroup;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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
    @Builder.Default
    private Map<TagGroup, Integer> mmr = new ConcurrentHashMap<>();

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    @Enumerated(EnumType.STRING)
    Map<RecommendType, Recommend> recommends;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    Map<Tag, Long> submissionCount = new ConcurrentHashMap<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    Map<Tag, Long> correctCount = new ConcurrentHashMap<>();

    public void addSubmit(Submission submission) {
        Tag answerTag = submission.getQuiz().getAnswerTag();
        Tag selectTag = submission.getSelectTag();
        submissionCount.merge(selectTag, +1L, Long::sum);
        if (selectTag.equals(answerTag))
            correctCount.merge(selectTag, +1L, Long::sum);
    }

    public void minusSubmit(Submission submission) {
        Tag answerTag = submission.getQuiz().getAnswerTag();
        Tag selectTag = submission.getSelectTag();
        submissionCount.merge(selectTag, -1L, Long::sum);
        if (selectTag.equals(answerTag))
            correctCount.merge(answerTag, -1L, Long::sum);
    }

    public Map<Tag, Double> getCorrectRate() {
        var ret = new HashMap<Tag, Double>();
        for (var tag : Tag.values()) {
            Long submissionCnt = submissionCount.getOrDefault(tag, 0L);
            Long correctCnt = correctCount.getOrDefault(tag, 0L);
            if (submissionCnt.equals(0L))
                ret.put(tag, 0.0);
            else
                ret.put(tag, (double) correctCnt / (double) submissionCnt);
        }
        return ret;
    }
}
