package vision.cotegory.controller.response;

import lombok.Data;
import vision.cotegory.entity.Submission;
import vision.cotegory.entity.tag.Tag;

import java.time.LocalDateTime;

@Data
public class SubmissionResponse {

    private Long quizId;
    private Long submissionId;

    private Double quizCorrectRate;

    private Tag selectTag;
    private Tag answerTag;
    private Boolean isCorrect;

    private LocalDateTime submitTime;
    private Integer playTime;

    public SubmissionResponse(Submission submission) {
        this.quizId = submission.getQuiz().getId();
        this.submissionId = submission.getId();

        this.quizCorrectRate = submission.getQuiz().getCorrectRate();

        this.selectTag = submission.getSelectTag();
        this.answerTag = submission.getQuiz().getAnswerTag();
        this.isCorrect = (submission.getSelectTag().equals(submission.getQuiz().getAnswerTag()));

        this.submitTime = submission.getSubmitTime();
        this.playTime = submission.getPlayTime();
    }
}
