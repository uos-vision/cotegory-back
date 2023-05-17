package vision.cotegory.entity.problem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import vision.cotegory.entity.Origin;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@SuperBuilder
@Getter
@NoArgsConstructor
public class ProgrammersProblem extends Problem{
    @Column(unique = true)
    private Integer problemNumber;

    private String title;
    private Integer timeLimit;
    private Integer memoryLimit;
    private String problemBody;
    private String problemInput;
    private String problemOutput;
    private String sampleInput;
    private String sampleOutput;

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public Integer getProblemNumber() {
        return this.problemNumber;
    }

    @Override
    public Integer getTimeLimit() {
        return this.timeLimit;
    }

    @Override
    public Integer getMemoryLimit() {
        return this.memoryLimit;
    }

    @Override
    public String getProblemBody() {
        return this.problemBody;
    }

    @Override
    public String getProblemInput() {
        return this.problemInput;
    }

    @Override
    public String getProblemOutput() {
        return this.problemOutput;
    }

    @Override
    public String getSampleInput() {
        return this.sampleInput;
    }

    @Override
    public String getSampleOutput() {
        return this.sampleOutput;
    }

    @Override
    public Origin getProblemOrigin() {
        return Origin.PROGRAMMERS;
    }

    @Override
    public String getUrl() {
        return String.format("https://school.programmers.co.kr/learn/courses/30/lessons/%d", problemNumber);
    }
}
