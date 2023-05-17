package vision.cotegory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Submission;
import vision.cotegory.repository.SubmissionRepository;
import vision.cotegory.service.dto.CreateSubmissionDto;

import java.time.LocalDateTime;
import java.util.List;

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

    public List<Submission> findAllByTime(Member member, LocalDateTime fromTime, LocalDateTime toTime) {
        return submissionRepository.findAllByMemberAndSubmitTimeBetween(member, fromTime, toTime);
    }

    public Page<Submission> findAllByPageable(Member member, Pageable pageable) {
        return submissionRepository.findAllByMember(member, pageable);
    }

    public List<Submission> findAll(Member member) {
        return submissionRepository.findAllByMember(member);
    }

}
