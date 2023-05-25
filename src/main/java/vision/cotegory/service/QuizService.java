package vision.cotegory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Quiz;
import vision.cotegory.entity.problem.Problem;
import vision.cotegory.entity.problem.ProblemMeta;
import vision.cotegory.repository.ProblemRepository;
import vision.cotegory.repository.QuizRepository;
import vision.cotegory.service.dto.CreateProblemMetaDto;
import vision.cotegory.service.dto.CreateQuizDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QuizService {

    private final QuizRepository quizRepository;
    private final ProblemRepository problemRepository;
    private final TagGroupService tagGroupService;
    private final ProblemMetaService problemMetaService;

    public void createQuiz(CreateQuizDto createQuizDto) {
        if (isNotAssignableTagGroup(createQuizDto))
            return;
        CreateProblemMetaDto createProblemMetaDto = CreateProblemMetaDto.builder()
                .title(createQuizDto.getTitle())
                .origin(createQuizDto.getOrigin())
                .url(createQuizDto.getUrl())
                .problemNumber(createQuizDto.getProblemNumber())
                .build();
        ProblemMeta problemMeta = problemMetaService.createProblemMeta(createProblemMetaDto);

        Problem problem = Problem.builder()
                .problemMeta(problemMeta)
                .tags(createQuizDto.getTags())
                .problemBody(createQuizDto.getProblemBody())
                .problemInput(createQuizDto.getProblemInput())
                .problemOutput(createQuizDto.getProblemOutput())
                .sampleInput(createQuizDto.getSampleInput())
                .sampleOutput(createQuizDto.getSampleOutput())
                .memoryLimit(createQuizDto.getMemoryLimit())
                .timeLimit(createQuizDto.getTimeLimit())
                .build();
        problemRepository.save(problem);

        Quiz quiz = Quiz.builder()
                .answerTag(createQuizDto.getAnswerTag())
                .tagGroup(createQuizDto.getTagGroup())
                .problem(problem)
                .activated(true)
                .build();
        quizRepository.save(quiz);
    }

    private boolean isNotAssignableTagGroup(CreateQuizDto createQuizDto) {
        return !tagGroupService.assignableGroups(createQuizDto.getTags())
                .containsKey(createQuizDto.getTagGroup());
    }


    public Optional<Quiz> findQuiz(Long id) {
        return quizRepository.findById(id);
    }

    public Quiz recommendQuiz(Member member) {
        int minDiff = 2000;
        Quiz target = null;
        List<Quiz> list = quizRepository.findAllByActivatedIsTrue();

        for (Quiz q : list) {
            int diff = Math.abs(q.getMmr() - member.getMmr().get(q.getTagGroup()));
            if (diff < minDiff) {
                log.info("[mmr]quizMmr = {}, memberMmr = {}, diff = {}",
                        q.getMmr(),
                        member.getMmr().get(q.getTagGroup()),
                        diff);

                minDiff = diff;
                target = q;
            }
        }
        return target;
    }
}
