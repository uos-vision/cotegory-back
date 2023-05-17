package vision.cotegory.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SubmissionPageInfoRequest {

    @NotNull(message = "PageNum is required")
    @Schema(example = "1", description = "조회하고자 하는 페이지 입니다.")
    private Integer pageNum;

    @NotNull(message = "Size is required")
    @Schema(example = "2", description = "조회하고자 하는 페이징 방식의 사이즈 입니다.")
    private Integer size;
}
