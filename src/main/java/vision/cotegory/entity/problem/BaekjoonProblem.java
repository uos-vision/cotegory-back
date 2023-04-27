package vision.cotegory.entity.problem;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import vision.cotegory.entity.Origin;

import javax.persistence.*;

@Entity
@SuperBuilder
@Getter
@NoArgsConstructor
public class BaekjoonProblem extends Problem{

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "problemNumber")
    private BaekjoonProblemPage problemPage;

    @Column(unique = true)
    private Integer problemNumber;

    private Integer level;

    @Override
    public String getTitle() {
        return problemPage.getTitle();
    }

    @Override
    public Integer getTimeLimit() {
        return problemPage.getTimeLimit();
    }

    @Override
    public Integer getMemoryLimit() {
        return problemPage.getMemoryLimit();
    }

    @Override
    public String getProblemBody() {
        return problemPage.getProblemBody();
    }

    @Override
    public String getProblemInput() {
        return problemPage.getProblemInput();
    }

    @Override
    public String getProblemOutput() {
        return problemPage.getProblemOutput();
    }

    @Override
    public String getSampleInput() {
        return problemPage.getSampleInput();
    }

    @Override
    public String getSampleOutput() {
        return problemPage.getSampleOutput();
    }

    @Override
    public Origin getProblemOrigin() {
        return Origin.BAEKJOON;
    }
}
