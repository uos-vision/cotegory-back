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
    String getTitle() {
        return this.title;
    }

    @Override
    Integer getProblemNumber() {
        return this.problemNumber;
    }

    @Override
    Integer getTimeLimit() {
        return this.timeLimit;
    }

    @Override
    Integer getMemoryLimit() {
        return this.memoryLimit;
    }

    @Override
    String getProblemBody() {
        return this.problemBody;
    }

    @Override
    String getProblemInput() {
        return this.problemInput;
    }

    @Override
    String getProblemOutput() {
        return this.problemOutput;
    }

    @Override
    String getSampleInput() {
        return this.sampleInput;
    }

    @Override
    String getSampleOutput() {
        return this.sampleOutput;
    }

    @Override
    Origin getProblemOrigin() {
        return Origin.PROGRAMMERS;
    }
}
