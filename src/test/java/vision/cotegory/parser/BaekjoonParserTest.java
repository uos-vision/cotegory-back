package vision.cotegory.parser;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vision.cotegory.entity.Tag;
import vision.cotegory.entity.TagGroup;

import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest
@Slf4j
class BaekjoonParserTest {

    @Autowired
    BaekjoonParser baekjoonParser;

    @Test
    void baekjoonParserTest() {
        final List<Integer> problemPages = baekjoonParser.notLogin()
                .problemList(Tag.DP.toBaekjoonCode(), 1)
                .getProblemPages();

        for (var problemPage : problemPages) {
            final List<Integer> problemNumbers = baekjoonParser.notLogin()
                    .problemList(Tag.DP.toBaekjoonCode(), problemPage)
                    .getProblemNumbers();
            for (var problemNumber : problemNumbers) {
                final Set<Tag> tags = baekjoonParser.onLogin().problem(problemNumber).getTags();
                final String title = baekjoonParser.notLogin().problem(problemNumber).getTitle();
                final Map<TagGroup, Tag> tagGroupTagMap = TagGroup.assignableGroups(tags);
                log.info("[{}] {} | {}", problemNumber, title, tagGroupTagMap);
            }
        }
    }



    @Test
    void baekjoonParserParallelTest() {
        final List<Integer> problemPages = baekjoonParser.notLogin()
                .problemList(Tag.DP.toBaekjoonCode(), 1)
                .getProblemPages();

        problemPages.parallelStream()
                .flatMap(problemPage -> baekjoonParser.notLogin()
                        .problemList(Tag.DP.toBaekjoonCode(), problemPage)
                        .getProblemNumbers().parallelStream())
                .forEach(problemNumber -> {
                    final Set<Tag> tags = baekjoonParser.onLogin().problem(problemNumber).getTags();
                    final Map<TagGroup, Tag> tagGroupTagMap = TagGroup.assignableGroups(tags);
                    log.info("{} | {}", problemNumber, tagGroupTagMap);
                });
    }
}