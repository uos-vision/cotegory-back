package vision.cotegory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vision.cotegory.entity.Origin;
import vision.cotegory.entity.problem.Problem;
import vision.cotegory.entity.problem.ProblemMeta;
import vision.cotegory.exception.exception.DuplicatedEntityException;
import vision.cotegory.exception.exception.NotExistEntityException;
import vision.cotegory.repository.ProblemMetaRepository;
import vision.cotegory.repository.ProblemRepository;
import vision.cotegory.service.dto.CreateCompanyProblemDto;
import vision.cotegory.service.dto.CreateProblemMetaDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class ProblemMetaService {

    private final ProblemRepository problemRepository;
    private final ProblemMetaRepository problemMetaRepository;

    private Problem todayRandomProblem = null;
    private final String filePath = "src/main/java/vision/cotegory/data/companyProblemCSV.csv";


    public Optional<Problem> findBaekjoonProblem(Integer problemNumber) {
        return problemRepository.findByProblemNumberAndOrigin(problemNumber, Origin.BAEKJOON);
    }

    public void updateTodayProblem() {
        List<Problem> problems = problemRepository.findAll();
        long count = problemRepository.count();
        todayRandomProblem = problems.get((int) (Math.random() * count));
    }

    public Problem findTodayProblem() {
        if (todayRandomProblem == null)
            updateTodayProblem();
        return todayRandomProblem;
    }

    public ProblemMeta findCompanyProblem() {
        List<Long> Ids = problemMetaRepository.findAllIdByOriginAndCompanyIsTrue();
        Long randId = Ids.get(ThreadLocalRandom.current().nextInt(0, Ids.size()));

        return problemMetaRepository.findById(randId).orElseThrow(NotExistEntityException::new);
    }

    public ProblemMeta createProblemMeta(CreateProblemMetaDto createProblemMetaDto) {
        if (problemMetaRepository.findByProblemNumberAndOrigin(
                createProblemMetaDto.getProblemNumber(),
                createProblemMetaDto.getOrigin()).isPresent())
            throw new DuplicatedEntityException();

        ProblemMeta problemMeta = ProblemMeta.builder()
                .url(createProblemMetaDto.getUrl())
                .title(createProblemMetaDto.getTitle())
                .origin(createProblemMetaDto.getOrigin())
                .problemNumber(createProblemMetaDto.getProblemNumber())
                .isCompany(createProblemMetaDto.getIsCompany())
                .build();
        return problemMetaRepository.save(problemMeta);
    }
}
