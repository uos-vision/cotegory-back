package vision.cotegory.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Submission;

import java.util.List;

@SpringBootTest
@ActiveProfiles("prod")
class SubmissionRepositoryTest {
    @Autowired
    SubmissionRepository submissionRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void test(){
        Member member = memberRepository.findByLoginIdAndActivatedIsTrue("member").get();
        List<Submission> submissions = submissionRepository.findAllByMemberAndIsSkippedIsFalse(member);
        System.out.println(submissions.size());
    }
}