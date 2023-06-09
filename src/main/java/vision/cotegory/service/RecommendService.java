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
import vision.cotegory.repository.ProblemMetaRepository;
import vision.cotegory.repository.RecommendRepository;
import vision.cotegory.problemloader.ai.AiRecommendProblemRequest;
import vision.cotegory.problemloader.ai.AiWebClient;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

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

        List<Tag> tagValues = Tag.valuesWithoutOthers();
        Tag answerTag = tagValues.get(ThreadLocalRandom.current().nextInt(0, tagValues.size()));

        Map<Tag, Double> correctRate = member.getCorrectRate();
        Set<Tag> tags = correctRate.entrySet().stream()
                .filter(entry -> !Objects.isNull(entry.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

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
        String tag = findTag(member).toKorean();
        AiRecommendProblemRequest aiRecommendProblemRequest = AiRecommendProblemRequest
                .builder()
                .handle(member.getBaekjoonHandle())
                .tag(tag)
                .cnt(1)
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
        ProblemMeta problemMeta = problemMetas.get((int) (Math.random() * problemMetas.size()));
        Recommend recommend = Recommend
                .builder()
                .problemMeta(problemMeta)
                .member(member)
                .recommendType(RecommendType.TODAY)
                .build();
        return recommendRepository.save(recommend);
    }


    public Recommend updateCompanyProblem(Member member) {
        List<ProblemMeta> companyProblemMetas = problemMetaRepository.findAllByIsCompanyIsTrue();
        if (companyProblemMetas.isEmpty())
            throw new NotExistEntityException();
        int randNum = (int) (Math.random() * companyProblemMetas.size());
        ProblemMeta companyProblem = companyProblemMetas.get(randNum);
        Recommend recommend = Recommend
                .builder()
                .problemMeta(companyProblem)
                .member(member)
                .recommendType(RecommendType.COMPANY)
                .build();
        return recommendRepository.save(recommend);
    }

    public Recommend findAIProblem(Member member) {
        if (!member.getRecommends().containsKey(RecommendType.AI))
            updateAIRecommend(member);
        return member.getRecommends().get(RecommendType.AI);
    }

    public Recommend findTodayProblem(Member member) {
        if (!member.getRecommends().containsKey(RecommendType.TODAY))
            updateTodayProblem(member);
        return member.getRecommends().get(RecommendType.TODAY);
    }

    public Recommend findCompanyProblem(Member member) {
        if (!member.getRecommends().containsKey(RecommendType.COMPANY))
            updateCompanyProblem(member);
        return member.getRecommends().get(RecommendType.COMPANY);
    }
}
