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
import vision.cotegory.utils.CSVUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProblemService {

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

    private List<List<String>> makeDefaultList() {
        List<List<String>> defaultlist = new ArrayList<>();
        defaultlist.add(new ArrayList<>());
        defaultlist.get(0).add("150370");
        defaultlist.get(0).add("개인정보 수집 유효기간");
        return defaultlist;
    } //삭제할예정

    public List<String> findCompanyProblem() {
        List<List<String>> file = CSVUtils.readCSV(this.filePath).orElse(makeDefaultList());
        int randNum = (int) (Math.random() * file.size()); //유저 Entity에 저장해야 하는지 검토 필요
        return file.get(randNum);
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
                .build();
        return problemMetaRepository.save(problemMeta);
    }

    public void createCompanyProblem(CreateCompanyProblemDto createCompanyProblemDto) {
        String data = String.format("%s,%d,%s",
                createCompanyProblemDto.getProblemName(),
                createCompanyProblemDto.getProblemNum(),
                createCompanyProblemDto.getOrigin());
        CSVUtils.writeCSV(this.filePath, data);
    }

    //서비스 클래스를 새로 만드는 것이 어떨까요
    public void addCompanyProblemMeta() {
        final String filePath = "src/main/java/vision/cotegory/data/companyProblemCSV.csv";
        List<List<String>> files = CSVUtils.readCSV(filePath).orElseThrow(NotExistEntityException::new);
        for(List<String> file : files) {
            Optional<ProblemMeta> problemMeta = problemMetaRepository.findByProblemNumberAndOrigin(Integer.parseInt(file.get(1)), Origin.valueOf(file.get(2)));
            if (problemMeta.isEmpty())
            {
                ProblemMeta saveProblemMeta = ProblemMeta
                        .builder()
                        .url("https://school.programmers.co.kr/learn/courses/30/lessons/" + file.get(1))
                        .problemNumber(Integer.parseInt(file.get(1)))
                        .origin(Origin.valueOf(file.get(2)))
                        .title(file.get(0))
                        .isCompany(true)
                                .build();
                problemMetaRepository.save(saveProblemMeta);
            }
        }
    }
}
