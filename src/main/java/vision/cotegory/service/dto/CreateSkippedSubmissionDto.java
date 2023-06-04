package vision.cotegory.service.dto;

import lombok.Builder;
import lombok.Data;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Quiz;

@Data
@Builder
public class CreateSkippedSubmissionDto {

    Member member;
    Quiz quiz;
}
