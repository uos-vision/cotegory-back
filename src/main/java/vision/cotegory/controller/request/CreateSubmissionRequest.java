package vision.cotegory.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import vision.cotegory.entity.tag.Tag;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateSubmissionRequest {

    @NotNull(message = "QuizId is required")
    private Long quizId;

    @NotNull(message = "SelectTag is required")
    @Schema(example = "DP")
    private Tag selectTag;

    @NotNull(message = "PlayTime is required")
    private Integer playTime;
}
