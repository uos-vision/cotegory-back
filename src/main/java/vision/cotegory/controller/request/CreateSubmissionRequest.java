package vision.cotegory.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Quiz;
import vision.cotegory.entity.Tag;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateSubmissionRequest {

    private Long quizId;

    @Schema(example = "DP")
    private Tag selectTag;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(example = "2023-05-03T08:10:52")
    private LocalDateTime submitTime;

    private Integer playTime;
}
