package vision.cotegory.service.dto;

import lombok.Builder;
import lombok.Data;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Quiz;
import vision.cotegory.entity.tag.Tag;

import java.time.LocalDateTime;

@Data
@Builder
public class CreateSubmissionDto {

    private Member member;

    private Quiz quiz;

    private Tag selectTag;

    private LocalDateTime submitTime;

    private Integer playTime;
}
