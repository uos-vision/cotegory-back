package vision.cotegory;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
public class SandBox {

    @Test
    void integerTest() {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher("2312번");
        if (matcher.find())
            System.out.println(matcher.group());
    }
}
