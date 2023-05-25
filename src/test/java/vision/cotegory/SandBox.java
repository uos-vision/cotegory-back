package vision.cotegory;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Role;
import vision.cotegory.problemloader.baekjoon.BaekjoonPageCrawler;
import vision.cotegory.repository.MemberRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@SpringBootTest
@Slf4j
public class SandBox {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testInserts() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .loginId("member" + i)
                    .pw(passwordEncoder.encode("1111"))
                    .activated(true)
                    .nickName("nickname" + i)
                    .roles(Set.of(Role.ROLE_USER))
                    .build();
            memberRepository.save(member);
        });
    }

    @Test
    public void sandBox() throws FileNotFoundException {
        for (int i = 0; i < 100; ++i){
            final int val = ThreadLocalRandom.current().nextInt(0, 10);
            System.out.println(val);
        }
    }
}

