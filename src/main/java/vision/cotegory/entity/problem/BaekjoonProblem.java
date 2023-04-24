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
    String getTitle() {
        return problemPage.getTitle();
    }

    @Override
    Integer getTimeLimit() {
        return problemPage.getTimeLimit();
    }

    @Override
    Integer getMemoryLimit() {
        return problemPage.getMemoryLimit();
    }

    @Override
    String getProblemBody() {
        return problemPage.getProblemBody();
    }

    @Override
    String getProblemInput() {
        return problemPage.getProblemInput();
    }

    @Override
    String getProblemOutput() {
        return problemPage.getProblemOutput();
    }

    @Override
    String getSampleInput() {
        return problemPage.getSampleInput();
    }

    @Override
    String getSampleOutput() {
        return problemPage.getSampleOutput();
    }

    @Override
    Origin getProblemOrigin() {
        return Origin.BAEKJOON;
    }
}
