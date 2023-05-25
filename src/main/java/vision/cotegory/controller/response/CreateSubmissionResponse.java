package vision.cotegory.controller.response;

import lombok.Data;
import vision.cotegory.entity.Submission;
import vision.cotegory.entity.tag.Tag;

@Data
public class CreateSubmissionResponse {
    private Long quizId;
    private Long submissionId;

    private Tag selectTag;
    private Tag answerTag;
    private Boolean isCorrect;

    public CreateSubmissionResponse(Submission submission) {
        this.quizId = submission.getQuiz().getId();
        this.submissionId = submission.getId();
        this.selectTag = submission.getSelectTag();
        this.answerTag = submission.getQuiz().getAnswerTag();
        this.isCorrect = (submission.getSelectTag().equals(submission.getQuiz().getAnswerTag()));
    }
}
