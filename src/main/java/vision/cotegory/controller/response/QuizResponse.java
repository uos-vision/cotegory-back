package vision.cotegory.controller.response;

import lombok.Data;
import vision.cotegory.entity.Origin;
import vision.cotegory.entity.Quiz;
import vision.cotegory.entity.tag.Tag;

@Data
public class QuizResponse {

    private Long quizId;
    private Tag answerTag;
    private TagGroupResponse tagGroupResponse;

    private Integer problemNumber;
    private Origin origin;
    private String title;
    private String url;

    private Integer mmr;

    private String problemBody;
    private String problemInput;
    private String problemOutput;
    private String sampleInput;
    private String sampleOutput;
    private Integer timeLimit;
    private Integer memoryLimit;

    private Double correctRate;

    public QuizResponse(Quiz quiz) {
        this.quizId = quiz.getId();
        this.answerTag = quiz.getAnswerTag();
        this.tagGroupResponse = new TagGroupResponse(quiz.getTagGroup());

        this.problemNumber = quiz.getProblem().getProblemMeta().getProblemNumber();
        this.origin = quiz.getProblem().getProblemMeta().getOrigin();
        this.title = quiz.getProblem().getProblemMeta().getTitle();
        this.url = quiz.getProblem().getProblemMeta().getUrl();
        this.mmr = quiz.getMmr();

        this.problemBody = quiz.getProblem().getProblemBody();
        this.problemInput = quiz.getProblem().getProblemInput();
        this.problemOutput = quiz.getProblem().getProblemOutput();
        this.sampleInput = quiz.getProblem().getSampleInput();
        this.sampleOutput = quiz.getProblem().getSampleOutput();
        this.timeLimit = quiz.getProblem().getTimeLimit();
        this.memoryLimit = quiz.getProblem().getMemoryLimit();
        this.correctRate = quiz.getCorrectRate();
    }
}
