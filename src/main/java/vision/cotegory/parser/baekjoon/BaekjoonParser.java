package vision.cotegory.parser.baekjoon;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vision.cotegory.entity.Tag;
import vision.cotegory.entity.TagGroup;
import vision.cotegory.parser.baekjoon.dto.BaekjoonProblemDto;

import java.util.List;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class BaekjoonParser {

    private final SolvedacWebClient solvedacWebClient;

    public List<Integer> getProblemPagesByTag(Tag tag) {
        BaekjoonProblemListParser baekjoonProblemListParser = new BaekjoonProblemListParser(tag.toBaekjoonCode());
        return baekjoonProblemListParser.getProblemPages();
    }

    public List<Integer> getProblemNumbers(Tag tag, Integer page){
        BaekjoonProblemListParser baekjoonProblemListParser = new BaekjoonProblemListParser(tag.toBaekjoonCode(), page);
        return baekjoonProblemListParser.getProblemNumbers();
    }

    public BaekjoonProblemDto getBaekjoonProblemDtoWithoutTag(Integer problemNumber){
        BaekjoonProblemParser baekjoonProblemParser = new BaekjoonProblemParser(problemNumber);
        Set<Tag> tags = solvedacWebClient.getTags(problemNumber);

        BaekjoonProblemDto baekjoonProblemDto = BaekjoonProblemDto.builder()
                .tags(tags)
                .problemNumber(baekjoonProblemParser.getProblemNumber())
                .title(baekjoonProblemParser.getTitle())
                .timeLimit(baekjoonProblemParser.getTimeLimit())
                .memoryLimit(baekjoonProblemParser.getMemoryLimit())
                .subMissionCount(baekjoonProblemParser.getSubmissionCount())
                .correctAnswerCount(baekjoonProblemParser.getCorrectAnswerCount())
                .correctUserCount(baekjoonProblemParser.getCorrectUserCount())
                .correctRate(baekjoonProblemParser.getCorrectRate())
                .problemInput(baekjoonProblemParser.getProblemInput())
                .problemOutput(baekjoonProblemParser.getProblemOutput())
                .problemBody(baekjoonProblemParser.getProblemBody())
                .sampleInput(baekjoonProblemParser.getSampleInput())
                .sampleOutput(baekjoonProblemParser.getSampleOutput())
                .build();

        log.info("[{}]{} : {}", baekjoonProblemDto.getProblemNumber(), baekjoonProblemDto.getTitle(), TagGroup.assignableGroups(tags));
        return baekjoonProblemDto;
    }


}
