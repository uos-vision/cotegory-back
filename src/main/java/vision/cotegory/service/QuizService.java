package vision.cotegory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Quiz;
import vision.cotegory.entity.Submission;
import vision.cotegory.entity.TagGroupConst;
import vision.cotegory.entity.problem.HandWriteProblem;
import vision.cotegory.entity.problem.ProgrammersProblem;
import vision.cotegory.exception.exception.NotProperTagGroupAssignException;
import vision.cotegory.repository.ProblemRepository;
import vision.cotegory.repository.QuizRepository;
import vision.cotegory.repository.SubmissionRepository;
import vision.cotegory.service.dto.CreateQuizDto;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final ProblemRepository problemRepository;
    private final TagGroupConst tagGroupConst;
    private final SubmissionRepository submissionRepository;

    public Quiz createProgrammersQuiz(CreateQuizDto createQuizDto) {

        if (!isAssignableTagGroup(createQuizDto))
            throw new NotProperTagGroupAssignException();

        ProgrammersProblem programmersProblem = ProgrammersProblem.builder()
                .problemNumber(createQuizDto.getProblemNumber())
                .title(createQuizDto.getTitle())
                .tags(createQuizDto.getTags())
                .timeLimit(createQuizDto.getTimeLimit())
                .memoryLimit(createQuizDto.getMemoryLimit())
                .problemBody(createQuizDto.getProblemBody())
                .problemInput(createQuizDto.getProblemInput())
                .problemOutput(createQuizDto.getProblemOutput())
                .sampleInput(createQuizDto.getSampleInput())
                .sampleOutput(createQuizDto.getSampleOutput())
                .build();
        problemRepository.save(programmersProblem);

        Quiz quiz = Quiz.builder()
                .answerTag(createQuizDto.getAnswerTag())
                .tagGroup(createQuizDto.getTagGroup())
                .problem(programmersProblem)
                .activated(true)
                .build();
        return quizRepository.save(quiz);
    }

    private boolean isAssignableTagGroup(CreateQuizDto createQuizDto) {
        return tagGroupConst.assignableGroups(createQuizDto.getTags())
                .containsKey(createQuizDto.getTagGroup());
    }

    public Quiz createHandWriteQuiz(CreateQuizDto createQuizDto) {
        HandWriteProblem handWriteProblem = HandWriteProblem.builder()
                .problemNumber(createQuizDto.getProblemNumber())
                .title(createQuizDto.getTitle())
                .tags(createQuizDto.getTags())
                .timeLimit(createQuizDto.getTimeLimit())
                .memoryLimit(createQuizDto.getMemoryLimit())
                .problemBody(createQuizDto.getProblemBody())
                .problemInput(createQuizDto.getProblemInput())
                .problemOutput(createQuizDto.getProblemOutput())
                .sampleInput(createQuizDto.getSampleInput())
                .sampleOutput(createQuizDto.getSampleOutput())
                .build();
        problemRepository.save(handWriteProblem);

        Quiz quiz = Quiz.builder()
                .answerTag(createQuizDto.getAnswerTag())
                .tagGroup(createQuizDto.getTagGroup())
                .problem(handWriteProblem)
                .activated(true)
                .build();
        return quizRepository.save(quiz);
    }

    public Quiz recommendQuiz(Member member) {
        int minDiff = 2000;
        Quiz target = null;
        List<Quiz> list = quizRepository.findAll();

        for(Quiz q : list)
        {
            int diff = Math.abs(q.getMmr()- member.getMmr().get(q.getTagGroup()));
            if (diff < minDiff)
            {
                minDiff = diff;
                target = q;
            }
        }
        return target;
    }

    public Double correctRate() {
        List<Submission> list = submissionRepository.findAll();
        int submitCount = list.size();
        int correctCount = 0;

        if (submitCount == 0)
            return 0.0;

        for (Submission s : list)
        {
            if (s.getSelectTag() == s.getQuiz().getAnswerTag())
                correctCount++;
        }


        return (double) correctCount / submitCount;
    }
}
