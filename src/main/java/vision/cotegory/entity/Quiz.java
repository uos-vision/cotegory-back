package vision.cotegory.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;
import vision.cotegory.entity.problem.Problem;

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

    @ElementCollection
    private final Map<Tag, Long> tagCount = new ConcurrentHashMap<>();

    @Builder
    public Quiz(Problem problem, Integer mmr, TagGroup tagGroup, Tag answerTag, Boolean activated) {
        this.problem = problem;
        this.tagGroup = tagGroup;
        this.answerTag = answerTag;
        this.activated = activated;
        this.mmr = mmr;
        problem.getQuizzes().add(this);
        tagGroup.getQuizzes().add(this);
    }

    public void increaseSubmitCount() {
        tagCount.merge(answerTag, 0L, (v1, v2) -> v1 + v2);
    }

    public void decreaseSubmitCount() {
        tagCount.merge(answerTag, 0L, (v1, v2) -> v1 - v2);
    }

    public Long getSubmitCount() {
        return tagCount.values().stream().reduce(0L, Long::sum);
    }

    public Long getCorrectCount() {
        return tagCount.getOrDefault(answerTag, 0L);
    }

    public Double getCorrectRate() {
        if (getSubmitCount().equals(0L))
            return 0.0;
        return (double) getCorrectCount() / (double) getSubmitCount();
    }

    public Map<Tag, Long> getSubmittedCountByTags() {
        return new HashMap<>(tagCount);
    }

    public Map<Tag, Double> getSubmittedCountRateByTags() {
        var ret = new HashMap<Tag, Double>();
        Long submitCount = getSubmitCount();
        for (var tag : tagCount.keySet()) {
            if (submitCount.equals(0L)) {
                ret.put(tag, 0.0);
                continue;
            }
            double rate = (double) tagCount.getOrDefault(tag, 0L) / (double) submitCount;
            ret.put(tag, rate);
        }
        return ret;
    }
}
