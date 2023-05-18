package vision.cotegory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vision.cotegory.entity.Origin;
import vision.cotegory.entity.problem.Problem;
import vision.cotegory.repository.ProblemRepository;
import vision.cotegory.service.dto.CreateCompanyProblemDto;
import vision.cotegory.utils.CSVUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;
    private Problem todayRandomProblem = null;
    private String filePath = "src/main/java/vision/cotegory/data/companyProblemCSV.csv";


    public Optional<Problem> findBaekjoonProblem(Integer problemNumber) {
        return problemRepository.findByProblemNumberAndOrigin(problemNumber, Origin.BAEKJOON);
    }

    public void updateTodayProblem() {
        List<Problem> problems = problemRepository.findAll();
        long count = problemRepository.count();
        todayRandomProblem = problems.get((int)(Math.random() * count));
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
    }

    public List<String> findCompanyProblem() {
        List<List<String>> file = CSVUtils.readCSV(this.filePath).orElse(makeDefaultList());
        Integer randNum = (int)(Math.random() * file.size()); //유저 Entity에 저장해야 하는지 검토 필요
        List<String> companyProblem = file.get(randNum);
        return companyProblem;
    }

    public void createCompanyProblem(CreateCompanyProblemDto createCompanyProblemDto) {
        String data = String.format("%s,%d,%s",
                createCompanyProblemDto.getProblemName(),
                createCompanyProblemDto.getProblemNum(),
                createCompanyProblemDto.getOrigin());
        CSVUtils.writeCSV(this.filePath, data);
    }
}
