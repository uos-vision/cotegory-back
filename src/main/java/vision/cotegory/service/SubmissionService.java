package vision.cotegory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Quiz;
import vision.cotegory.entity.Submission;
import vision.cotegory.entity.tag.Tag;
import vision.cotegory.exception.exception.NotProperTagGroupAssignException;
import vision.cotegory.repository.SubmissionRepository;
import vision.cotegory.service.dto.CreateSkippedSubmissionDto;
import vision.cotegory.service.dto.CreateSubmissionDto;
import vision.cotegory.utils.ElOUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final ElOUtils elOUtils;

    public Submission createSkippedSubmission(CreateSkippedSubmissionDto createSkippedSubmissionDto) {
        Member member = createSkippedSubmissionDto.getMember();
        Quiz quiz = createSkippedSubmissionDto.getQuiz();
        Submission submission = Submission.builder()
                .member(member)
                .quiz(quiz)
                .isSkipped(true)
                .build();

        return submissionRepository.save(submission);
    }

    public Submission createSubmission(CreateSubmissionDto createSubmissionDto) {
        Member member = createSubmissionDto.getMember();
        Quiz quiz = createSubmissionDto.getQuiz();
        Tag selectTag = createSubmissionDto.getSelectTag();

        if(!quiz.getTagGroup().getTags().contains(selectTag))
            throw new NotProperTagGroupAssignException("고른 태그는 선지에 존재하지 않습니다");

        Submission submission = Submission.builder()
                .member(member)
                .quiz(quiz)
                .selectTag(selectTag)
                .submitTime(createSubmissionDto.getSubmitTime())
                .playTime(createSubmissionDto.getPlayTime())
                .isSkipped(false)
                .build();

        member.addSubmit(submission);
        quiz.increaseSubmitCount(submission);

        //멤버의 MMR을 업데이트 하는 부분
        Pair<Integer, Integer> elo = elOUtils.updateElo(member, submission);
        member.getMmr().put(quiz.getTagGroup(), elo.getFirst());
        quiz.setMmr(elo.getSecond());
        log.info(elo.getFirst() + " " + elo.getSecond());

        return submissionRepository.save(submission);
    }

    public Page<Submission> findAllByTime(Member member, LocalDateTime fromTime, LocalDateTime toTime, Pageable pageable) {
        return submissionRepository.findAllByMemberAndSubmitTimeBetween(member, fromTime, toTime, pageable);
    }

    public Page<Submission> findAllByPageable(Member member, Pageable pageable) {
        return submissionRepository.findAllByMember(member, pageable);
    }

    public List<Submission> findAll(Member member) {
        return submissionRepository.findAllByMember(member);
    }

}
