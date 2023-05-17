package vision.cotegory.controller.response;

import lombok.Data;
import vision.cotegory.entity.Quiz;

@Data
public class QuizResponse {

    private Long id;

    private String tagGroup;

    private String answerTag;

    private Boolean activated;

    private String problemBody;

    private String problemInput;

    private String problemOutput;

    private String sampleInput;

    private String sampleOutput;

    private String title;

    private String url;
    private Integer timeLimit;
    private Integer memoryLimit;
    private Long subMissionCount;
    private Long correctUserCount;

    public QuizResponse(Quiz quiz) {
        this.id = quiz.getId();
        this.tagGroup = quiz.getTagGroup().getName();
        this.answerTag = quiz.getAnswerTag().toString();
        this.activated = quiz.getActivated();
        this.problemBody = quiz.getProblem().getProblemBody();
        this.problemInput = quiz.getProblem().getProblemInput();
        this.problemOutput = quiz.getProblem().getProblemOutput();
        this.sampleInput = quiz.getProblem().getSampleInput();
        this.sampleOutput = quiz.getProblem().getSampleOutput();
        this.title = quiz.getProblem().getTitle();
        this.timeLimit = quiz.getProblem().getTimeLimit();
        this.memoryLimit = quiz.getProblem().getMemoryLimit();
        this.subMissionCount = quiz.getSubmitCount();
        this.correctUserCount = quiz.getCorrectCount();
        this.url = quiz.getProblem().getUrl();
    }
}
