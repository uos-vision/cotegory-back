package vision.cotegory.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vision.cotegory.entity.problem.Problem;
import vision.cotegory.entity.tag.Tag;
import vision.cotegory.entity.tag.TagGroup;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Entity
@NoArgsConstructor
@Getter
public class Quiz {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Problem problem;

    @ManyToOne(fetch = FetchType.LAZY)
    private TagGroup tagGroup;

    @Enumerated(EnumType.STRING)
    private Tag answerTag;

    @Setter
    private Boolean activated;

    @Setter
    private Integer mmr = 1200;

    @ElementCollection(fetch = FetchType.LAZY)
    @MapKeyEnumerated(EnumType.STRING)
    private final Map<Tag, Long> tagCount = new ConcurrentHashMap<>();

    @Builder
    public Quiz(Problem problem, TagGroup tagGroup, Tag answerTag, Boolean activated) {
        this.problem = problem;
        this.tagGroup = tagGroup;
        this.answerTag = answerTag;
        this.activated = activated;
        problem.getQuizzes().add(this);
        tagGroup.getQuizzes().add(this);
    }

    public void increaseSubmitCount(Submission submission) {
        tagCount.merge(submission.getSelectTag(), +1L, Long::sum);
    }

    public void decreaseSubmitCount(Submission submission) {
        tagCount.merge(submission.getSelectTag(), -1L, Long::sum);
    }

    public Long getSubmitCount() {
        return tagCount.values().stream().reduce(0L, Long::sum);
    }

    public Long getCorrectCount() {
        return tagCount.getOrDefault(answerTag, 0L);
    }

    public Double getCorrectRate() {
        if (getSubmitCount().equals(0L))
            return null;
        return (double) getCorrectCount() / (double) getSubmitCount();
    }

    public Map<Tag, Long> getSubmittedCountByTags() {
        HashMap<Tag, Long> ret = new HashMap<>(tagCount);
        tagGroup.getTags().forEach(tag -> ret.putIfAbsent(tag, 0L));
        return ret;
    }

    public Map<Tag, Double> getSubmittedCountRateByTags() {
        var ret = new HashMap<Tag, Double>();
        Long submitCount = getSubmitCount();
        for (var tag : tagGroup.getTags()) {
            if (submitCount.equals(0L)) {
                ret.put(tag, null);
                continue;
            }
            double rate = (double) tagCount.getOrDefault(tag, 0L) / (double) submitCount;
            ret.put(tag, rate);
        }
        return ret;
    }
}
