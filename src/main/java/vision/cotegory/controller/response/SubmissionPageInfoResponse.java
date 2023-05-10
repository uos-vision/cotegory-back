package vision.cotegory.controller.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class SubmissionPageInfoResponse {
    private Integer totalDataCnt;
    private Integer totalPages;

    private boolean isLastPage;
    private boolean isFirstPage;

    private Integer requestPage;
    private Integer requestSize;
}
