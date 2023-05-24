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
import vision.cotegory.repository.SubmissionRepository;
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

    public Submission createSubmission(CreateSubmissionDto createSubmissionDto) {
        Member member = createSubmissionDto.getMember();
        Quiz quiz = createSubmissionDto.getQuiz();

        Submission submission = Submission.builder()
                .member(member)
                .quiz(createSubmissionDto.getQuiz())
                .selectTag(createSubmissionDto.getSelectTag())
                .submitTime(createSubmissionDto.getSubmitTime())
                .playTime(createSubmissionDto.getPlayTime())
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
