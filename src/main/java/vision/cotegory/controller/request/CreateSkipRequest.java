package vision.cotegory.controller.request;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateSkipRequest {
    @NotNull
    private Long quizId;
}
