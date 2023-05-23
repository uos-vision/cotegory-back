package vision.cotegory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Origin;
import vision.cotegory.entity.Recommend;
import vision.cotegory.entity.RecommendType;
import vision.cotegory.entity.problem.ProblemMeta;
import vision.cotegory.entity.tag.Tag;
import vision.cotegory.exception.exception.NotExistEntityException;
import vision.cotegory.exception.exception.NotExistPathException;
import vision.cotegory.repository.ProblemMetaRepository;
import vision.cotegory.repository.RecommendRepository;
import vision.cotegory.utils.CSVUtils;
import vision.cotegory.webclient.ai.AiRecommendProblemRequest;
import vision.cotegory.webclient.ai.AiWebClient;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class RecommendService {
    private final ProblemMetaRepository problemMetaRepository;
    private final RecommendRepository recommendRepository;

    private final AiWebClient aiWebClient;

    private Tag findTag(Member member) {
        double correctSum = 0.0;
        double tmp = 0.0;
        Tag answerTag = Tag.DP;
        Map<Tag, Double> correctRate = member.getCorrectRate();
        Set<Tag> tags = correctRate.keySet();

        for (Tag tag : tags) {
            correctSum += 1 - correctRate.get(tag);
        }

        double randNum = Math.random() * correctSum;

        for (Tag tag : tags) {
            tmp += (1 - correctRate.get(tag));
            if (tmp > randNum) {
                answerTag = tag;
                break;
            }
        }
        return answerTag;
    }

    public Recommend updateAIRecommend(Member member) {
        String tag = findTag(member).toString();
        AiRecommendProblemRequest aiRecommendProblemRequest = AiRecommendProblemRequest
                .builder()
                .handle(member.getBaekjoonHandle())
                .tag(tag)
                .cnt(1)
                .model("EASE")
                .build();
        List<Integer> list = aiWebClient.getRecommendProblemNumbers(aiRecommendProblemRequest);
        ProblemMeta problemMeta = problemMetaRepository.findByProblemNumberAndOrigin(list.get(0), Origin.BAEKJOON).orElseThrow(NotExistEntityException::new);
        Recommend recommend = Recommend
                .builder()
                .problemMeta(problemMeta)
                .member(member)
                .recommendType(RecommendType.AI)
                .build();
        return recommendRepository.save(recommend);
    }

    public Recommend updateTodayProblem(Member member) {
        List<ProblemMeta> problemMetas = problemMetaRepository.findAll();
        long count = problemMetaRepository.count();
        ProblemMeta problemMeta = problemMetas.get((int) (Math.random() * count));
        Recommend recommend = Recommend
                .builder()
                .problemMeta(problemMeta)
                .member(member)
                .recommendType(RecommendType.TODAY)
                .build();
        return recommendRepository.save(recommend);
    }

    public Recommend updateCompanyProblem(Member member) {
        final String filePath = "src/main/java/vision/cotegory/data/companyProblemCSV.csv";
        //별로 좋은 방식은 아닌거 같으나 유지 하겠습니다.
        List<List<String>> file = CSVUtils.readCSV(filePath).orElseThrow(NotExistPathException::new);
        int randNum = (int) (Math.random() * file.size());
        List<String> companyProblem = file.get(randNum);
        Recommend recommend = Recommend
                .builder()
                .problemMeta(problemMetaRepository.findByProblemNumberAndOrigin(
                        Integer.parseInt(companyProblem.get(1)),
                        Origin.valueOf(companyProblem.get(2))).orElseThrow(NotExistEntityException::new)
                )
                .member(member)
                .recommendType(RecommendType.TODAY)
                .build();
        return recommendRepository.save(recommend);
    }

    public ProblemMeta findAIProblem(Member member) {
        return member.getRecommends().getOrDefault(RecommendType.AI, updateAIRecommend(member)).getProblemMeta();
    }

    public ProblemMeta findTodayProblem(Member member) {
        return member.getRecommends().getOrDefault(RecommendType.TODAY, updateTodayProblem(member)).getProblemMeta();
    }

    public ProblemMeta findCompanyProblem(Member member) {
        return member.getRecommends().getOrDefault(RecommendType.COMPANY, updateCompanyProblem(member)).getProblemMeta();
    }
}
