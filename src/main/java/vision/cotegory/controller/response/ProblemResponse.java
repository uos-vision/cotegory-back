package vision.cotegory.controller.response;
import lombok.Data;
import vision.cotegory.entity.problem.BaekjoonProblem;
import vision.cotegory.entity.problem.Problem;
import vision.cotegory.entity.problem.ProgrammersProblem;

@Data
public class ProblemResponse {

    private String title;
    private Integer problemNumber;
    private String url;

    public ProblemResponse(String title, Integer problemNumber, String url) {
        this.title = title;
        this.problemNumber = problemNumber;
        this.url = url;
    }

    public ProblemResponse(Problem problem) {
        this.title = problem.getTitle();
        this.problemNumber = problem.getProblemNumber();
        this.url = problem.getUrl();
    }
}
