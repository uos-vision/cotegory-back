package vision.cotegory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vision.cotegory.entity.problem.BaekjoonProblem;
import vision.cotegory.entity.problem.Problem;
import vision.cotegory.entity.problem.ProgrammersProblem;
import vision.cotegory.repository.BaekjoonProblemRepository;
import vision.cotegory.repository.ProblemRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final BaekjoonProblemRepository baekjoonProblemRepository;

    public Optional<BaekjoonProblem> findBaekjoonProblem(Integer problemNumber) {
        return baekjoonProblemRepository.findByProblemNumber(problemNumber);
    }

    //오늘의 추천과 기업문제 추천 서비스 개발 예정
}
