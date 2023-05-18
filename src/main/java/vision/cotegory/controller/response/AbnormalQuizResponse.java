package vision.cotegory.controller.response;

import lombok.Data;
import vision.cotegory.entity.AbnormalQuiz;
import vision.cotegory.entity.tag.Tag;

import java.util.Map;

@Data
public class AbnormalQuizResponse {
    private Long abnormalQuizId;
    private Long quizId;

    private Long submitCount;
    private Double correctRate;
    private Tag answerTag;
    private Map<Tag, Long> submittedTagCount;
    private Map<Tag, Double> submittedTagRateCount;

    private ProblemMetaResponse problemMetaResponse;

    public AbnormalQuizResponse(AbnormalQuiz abnormalQuiz){
        this.abnormalQuizId = abnormalQuiz.getId();
        this.quizId = abnormalQuiz.getQuiz().getId();
        this.submitCount = abnormalQuiz.getQuiz().getSubmitCount();
        this.correctRate = abnormalQuiz.getQuiz().getCorrectRate();
        this.answerTag = abnormalQuiz.getQuiz().getAnswerTag();
        this.submittedTagCount = abnormalQuiz.getQuiz().getSubmittedCountByTags();
        this.submittedTagRateCount = abnormalQuiz.getQuiz().getSubmittedCountRateByTags();
        this.problemMetaResponse = new ProblemMetaResponse(abnormalQuiz.getQuiz().getProblem().getProblemMeta());
    }
}
