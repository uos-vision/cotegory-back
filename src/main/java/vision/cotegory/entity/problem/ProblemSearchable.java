package vision.cotegory.entity.problem;

import vision.cotegory.entity.Origin;

public interface ProblemSearchable {
    String getTitle();
    Integer getProblemNumber();
    Integer getTimeLimit();
    Integer getMemoryLimit();

    String getProblemBody();
    String getProblemInput();
    String getProblemOutput();

    String getSampleInput();
    String getSampleOutput();
    Origin getProblemOrigin();
}
