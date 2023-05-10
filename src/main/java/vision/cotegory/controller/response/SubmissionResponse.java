package vision.cotegory.controller.response;

import lombok.Builder;
import lombok.Data;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Quiz;
import vision.cotegory.entity.Submission;
import vision.cotegory.entity.Tag;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class SubmissionResponse {

    private Long quizId;

    private Tag selectTag;

    private LocalDateTime submitTime;

    private Integer playTime;

    public SubmissionResponse(Submission submission) {
        this.quizId = submission.getQuiz().getId();
        this.selectTag = submission.getSelectTag();
        this.submitTime = submission.getSubmitTime();
        this.playTime = submission.getPlayTime();
    }
}
