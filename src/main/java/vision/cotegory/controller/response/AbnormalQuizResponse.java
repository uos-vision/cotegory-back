package vision.cotegory.controller.response;

import lombok.Data;
import vision.cotegory.entity.AbnormalQuiz;
import vision.cotegory.entity.Origin;
import vision.cotegory.entity.Tag;
import vision.cotegory.entity.problem.Problem;

import java.util.Map;

@Data
public class AbnormalQuizResponse {
    private Long abnormalQuizId;
    private Long quizId;

    private Long submitCount;
    private Double correctRate;
    private Tag answerTag;
    private Map<Tag, Long> selectedTagCount;

    private String title;
    private Origin origin;
    private Integer problemNumber;

    public AbnormalQuizResponse(AbnormalQuiz abnormalQuiz){
        this.abnormalQuizId = abnormalQuiz.getId();
        this.quizId = abnormalQuiz.getQuiz().getId();
        this.submitCount = abnormalQuiz.getSubmitCount();
        this.correctRate = abnormalQuiz.getCorrectRate();
        this.answerTag = abnormalQuiz.getQuiz().getAnswerTag();
        this.selectedTagCount = abnormalQuiz.getSelectedTagCount();

        Problem problem = abnormalQuiz.getQuiz().getProblem();
        this.title = problem.getTitle();
        this.origin = problem.getProblemOrigin();
        this.problemNumber = problem.getProblemNumber();
    }
}
