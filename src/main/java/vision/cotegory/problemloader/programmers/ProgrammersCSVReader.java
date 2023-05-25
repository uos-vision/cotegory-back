package vision.cotegory.problemloader.programmers;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vision.cotegory.entity.Origin;
import vision.cotegory.entity.problem.ProblemMeta;
import vision.cotegory.repository.ProblemMetaRepository;

import java.io.FileReader;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProgrammersCSVReader {

    private final ProblemMetaRepository problemMetaRepository;

    public void readCSV() {
        try (
                CSVReader csvReader = new CSVReaderBuilder(new FileReader("src/main/java/vision/cotegory/problemloader/programmers/programmersKaKao.csv"))
                        .withSkipLines(1)
                        .build()
        ) {
            csvReader.forEach(line ->{
                String title = line[0];
                Integer problemNumber = Integer.parseInt(line[1]);

                if(problemMetaRepository.findByProblemNumberAndOrigin(problemNumber, Origin.PROGRAMMERS).isPresent()){
                    log.info("[skipMeta]{}번 problemMeta은 이미 ProblemMetaRepo에 존재하므로 skip됩니다", problemNumber);
                    return;
                }

                ProblemMeta problemMeta = ProblemMeta.builder()
                        .title(title)
                        .problemNumber(problemNumber)
                        .origin(Origin.PROGRAMMERS)
                        .url(String.format("https://school.programmers.co.kr/learn/courses/30/lessons/%d", problemNumber))
                        .isCompany(true)
                        .build();

                problemMetaRepository.save(problemMeta);
                log.info("[saveMeta]{}번({})", problemMeta.getProblemNumber(), problemMeta.getTitle());
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
