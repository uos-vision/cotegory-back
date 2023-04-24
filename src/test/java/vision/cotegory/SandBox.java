package vision.cotegory;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
public class SandBox {

    @Test
    void integerTest() {
        final Abc bcd = new Bcd();
        System.out.println(bcd.getData());
    }

    static abstract class Abc{
        abstract Integer getData();
    }

    static class Bcd extends Abc{
        @Override
        Integer getData() {
            return 10;
        }
    }
}
