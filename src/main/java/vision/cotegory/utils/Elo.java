package vision.cotegory.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Quiz;
import vision.cotegory.entity.problem.Problem;

@RequiredArgsConstructor
public class Elo {

    private static double correctRate;

    public void updateCorrectRate(double correctRate) {
        this.correctRate = 0.0;
    }

    //반환값의 첫인자는 member의 elo, 두번째 인자는 quiz의 elo이다.
    public Pair<Integer, Integer> updateElo(Member member, Quiz quiz, boolean correct){

        Integer problemMmr = quiz.getProblem().getMmr();
        Integer memberMmr = member.getMmr().get(quiz.getTagGroup());
        double predictedValue;
        double actualValue;
        int weight = 20;
        int diffMmr = 600;
        double kFactorMem;
        double kFactorPro;

        actualValue = correct? 1 : 0;
        kFactorMem = correct? weight * (1 - correctRate) : weight * correctRate;
        kFactorPro = correct? weight * correctRate : weight * (1 - correctRate);
        predictedValue = 1 / (Math.pow(10, (memberMmr - problemMmr)/diffMmr) + 1);

        memberMmr = (int)(memberMmr + kFactorMem * (actualValue - predictedValue));
        problemMmr = (int)(problemMmr + kFactorPro * (Math.abs(actualValue - 1) - Math.abs(predictedValue - 1)));

        return Pair.of(memberMmr, problemMmr);
    }
}
