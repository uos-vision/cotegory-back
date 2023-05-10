package vision.cotegory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vision.cotegory.entity.Submission;
import vision.cotegory.repository.SubmissionRepository;
import vision.cotegory.service.dto.CreateSubmissionDto;

@Service
@RequiredArgsConstructor
@Transactional
public class SubmissionService {
    private final SubmissionRepository submissionRepository;

    public Submission createSubmission(CreateSubmissionDto createSubmissionDto)
    {
        Submission submission = Submission.builder()
                .member(createSubmissionDto.getMember())
                .quiz(createSubmissionDto.getQuiz())
                .selectTag(createSubmissionDto.getSelectTag())
                .submitTime(createSubmissionDto.getSubmitTime())
                .playTime(createSubmissionDto.getPlayTime())
                .build();

        return submissionRepository.save(submission);
    }

}
