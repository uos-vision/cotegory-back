package vision.cotegory.controller.response;

import lombok.Data;
import vision.cotegory.entity.Quiz;
import vision.cotegory.entity.problem.ProblemContents;

@Data
public class QuizResponse {

    private Long id;
    private String tagGroup;
    private String answerTag;
    private Boolean activated;

    private ProblemMetaResponse problemMetaResponse;
    private ProblemContents problemContents;

    public QuizResponse(Quiz quiz) {
        this.id = quiz.getId();
        this.tagGroup = quiz.getTagGroup().getName();
        this.answerTag = quiz.getAnswerTag().toString();
        this.activated = quiz.getActivated();

        this.problemMetaResponse = new ProblemMetaResponse(quiz.getProblem().getProblemMeta());
        this.problemContents = quiz.getProblem().getProblemContents();
    }
}
