package vision.cotegory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Quiz;
import vision.cotegory.entity.tag.TagGroupConst;
import vision.cotegory.entity.problem.Problem;
import vision.cotegory.entity.problem.ProblemMeta;
import vision.cotegory.repository.ProblemMetaRepository;
import vision.cotegory.repository.ProblemRepository;
import vision.cotegory.repository.QuizRepository;
import vision.cotegory.service.dto.CreateQuizDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizService {

    private final QuizRepository quizRepository;
    private final ProblemRepository problemRepository;
    private final ProblemMetaRepository problemMetaRepository;
    private final TagGroupConst tagGroupConst;

    public void createQuiz(CreateQuizDto createQuizDto) {
        if (isNotAssignableTagGroup(createQuizDto))
            return;
        ProblemMeta problemMeta = new ProblemMeta(createQuizDto.getProblemMetaContents());
        problemMetaRepository.save(problemMeta);

        Problem problem = Problem.builder()
                .problemMeta(problemMeta)
                .tags(createQuizDto.getTags())
                .problemContents(createQuizDto.getProblemContents())
                .build();
        problemRepository.save(problem);

        Quiz quiz = Quiz.builder()
                .activated(true)
                .tagGroup(createQuizDto.getTagGroup())
                .problem(problem)
                .answerTag(createQuizDto.getAnswerTag())
                .build();
        quizRepository.save(quiz);
    }

    private boolean isNotAssignableTagGroup(CreateQuizDto createQuizDto) {
        return !tagGroupConst.assignableGroups(createQuizDto.getTags())
                .containsKey(createQuizDto.getTagGroup());
    }


    public Optional<Quiz> findQuiz(Long id) {
        return quizRepository.findById(id);
    }

    public Quiz recommendQuiz(Member member) {
        int minDiff = 2000;
        Quiz target = null;
        List<Quiz> list = quizRepository.findAll();

        for (Quiz q : list) {
            int diff = Math.abs(q.getMmr() - member.getMmr().get(q.getTagGroup()));
            if (diff < minDiff) {
                minDiff = diff;
                target = q;
            }
        }
        return target;
    }
}
