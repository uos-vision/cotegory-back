package vision.cotegory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vision.cotegory.crawler.ai.AiRecommendProblemRequest;
import vision.cotegory.crawler.ai.AiWebClient;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Recommend;
import vision.cotegory.entity.RecommendType;
import vision.cotegory.entity.Tag;
import vision.cotegory.entity.problem.BaekjoonProblem;
import vision.cotegory.entity.problem.Problem;
import vision.cotegory.exception.exception.NotExistEntityException;
import vision.cotegory.repository.BaekjoonProblemRepository;
import vision.cotegory.repository.ProblemRepository;
import vision.cotegory.repository.RecommendRepository;
import vision.cotegory.service.dto.CreateCompanyProblemDto;
import vision.cotegory.utils.CSVUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecommendService {
    private final BaekjoonProblemRepository baekjoonProblemRepository;
    private final ProblemRepository problemRepository;
    private final RecommendRepository recommendRepository;
    private final AiWebClient aiWebClient;
    private String filePath = "src/main/java/vision/cotegory/data/companyProblemCSV.csv";


    public Recommend updateAIRecommend(Member member) {
        String dummyTag = String.valueOf(Tag.DP); // tag관련 로직을 작성할 예정입니다.
        AiRecommendProblemRequest aiRecommendProblemRequest = AiRecommendProblemRequest
                .builder()
                .handle(member.getBaekjoonHandle())
                .tag(dummyTag)
                .cnt(1)
                .model("EASE")
                .build();
        List<Integer> list = aiWebClient.getRecommendProblemNumbers(aiRecommendProblemRequest);
        BaekjoonProblem baekjoonProblem = baekjoonProblemRepository.findByProblemNumber(list.get(0)).orElseThrow(NotExistEntityException::new);
        Recommend recommend = Recommend
                .builder()
                .problem(baekjoonProblem)
                .member(member)
                .recommendType(RecommendType.AI)
                .build();
        return recommendRepository.save(recommend);
    }

    public Recommend updateTodayProblem(Member member) {
        List<Problem> problems = problemRepository.findAll();
        long count = problemRepository.count();
        Problem problem = problems.get((int)(Math.random() * count));
        Recommend recommend = Recommend
                .builder()
                .problem(problem)
                .member(member)
                .recommendType(RecommendType.TODAY)
                .build();
        return recommendRepository.save(recommend);
    }

    public Recommend updateCompanyProblem(Member member) {
        List<List<String>> file = CSVUtils.readCSV(this.filePath).orElse(makeDefaultList());
        Integer randNum = (int)(Math.random() * file.size()); //유저 Entity에 저장해야 하는지 검토 필요
        List<String> companyProblem = file.get(randNum);
        Recommend recommend = Recommend
                .builder()
                .problem(problem)
                .member(member)
                .recommendType(RecommendType.TODAY)
                .build();
        return recommendRepository.save(recommend);
    }

    public Problem findAIProblem(Member member) {
        member.getRecommends().putIfAbsent()
        return member.getRecommends().get(RecommendType.AI).getProblem();
    }

    public Problem findTodayProblem(Member member) {
        return member.getRecommends().get(RecommendType.TODAY).getProblem();
    }

    public Problem findCompanyProblem(Member member) {
        return member.getRecommends().get(RecommendType.COMPANY).getProblem();
    }

    public void createCompanyProblem(CreateCompanyProblemDto createCompanyProblemDto) {
        String data = String.format("%s,%d,%s",
                createCompanyProblemDto.getProblemName(),
                createCompanyProblemDto.getProblemNum(),
                createCompanyProblemDto.getOrigin());
        CSVUtils.writeCSV(this.filePath, data);
    }
}
