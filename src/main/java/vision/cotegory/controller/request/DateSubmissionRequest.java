package vision.cotegory.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DateSubmissionRequest {

    @NotNull(message = "FromTime is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(example = "2023-05-02T08:10:52")
    private LocalDateTime fromTime;


    @NotNull(message = "ToTime is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(example = "2023-05-10T08:10:52")
    private LocalDateTime toTime;
}
