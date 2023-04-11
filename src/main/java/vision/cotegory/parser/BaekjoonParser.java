package vision.cotegory.parser;

import com.nimbusds.jose.shaded.asm.ex.ConvertException;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import vision.cotegory.entity.Tag;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class BaekjoonParser {

    private final WebDriver webDriver;

    public OnLoginProblemLoader onLogin() {
        return new OnLoginProblemLoader();
    }

    public NotLoginProblemLoader notLogin() {
        return new NotLoginProblemLoader();
    }

    public class OnLoginProblemLoader {
        public OnLoginProblem problem(Integer problemNumber) {
            return new OnLoginProblem(problemNumber);
        }
    }

    public class NotLoginProblemLoader {
        public NotLoginProblem problem(Integer problemNumber) {
            return new NotLoginProblem(problemNumber);
        }

        public ProblemList problemList(Integer algoCode, Integer page) {
            return new ProblemList(algoCode, page);
        }
    }


    public class OnLoginProblem {
        private final String LOGIN_ID = "cotegory";
        private final String PW = "vision";

        private OnLoginProblem(Integer problemNumber) {
            webDriver.get("https://www.acmicpc.net/login?next=%2F");

            if (!webDriver.getCurrentUrl().equals("https://www.acmicpc.net/login?next=%2F")) {
                webDriver.get(String.format("https://www.acmicpc.net/problem/%d", problemNumber));
                return;
            }

            webDriver.findElement(new By.ByName("login_user_id")).sendKeys(LOGIN_ID);
            webDriver.findElement(new By.ByName("login_password")).sendKeys(PW);
            webDriver.findElement(new By.ByXPath("//*[@id=\"submit_button\"]")).click();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            webDriver.get(String.format("https://www.acmicpc.net/problem/%d", problemNumber));
        }

        public Set<Tag> getTags() {
            webDriver.findElement(By.cssSelector("#problem_tags > div.problem-text > p > a")).click();

            return webDriver.findElement(By.cssSelector("#problem_tags > div.spoiler > ul"))
                    .findElements(By.tagName("li"))
                    .stream()
                    .map(WebElement::getText)
                    .map(Tag::of)
                    .collect(Collectors.toSet());
        }
    }

    public class NotLoginProblem {

        private NotLoginProblem(Integer problemNumber) {
            String url = String.format("https://www.acmicpc.net/problem/%d", problemNumber);
            if(webDriver.getCurrentUrl().equals(url))
                return;
            webDriver.get(url);
        }

        public String getTitle() {
            return getContent("#problem_title");
        }

        public int getProblemNumber() {
            return contentParseInt(getContent(
                    "body > div.wrapper > div.container.content > div.row > div:nth-child(2) > ul > li.active > a"
            ));
        }

        public int getTimeLimit() {
            return contentParseInt(getContent("#problem-info > tbody > tr > td:nth-child(1)"));
        }

        public int getMemoryLimit() {
            return contentParseInt(getContent("#problem-info > tbody > tr > td:nth-child(2)"));
        }

        public int getSubmissionCount() {
            return contentParseInt(getContent("#problem-info > tbody > tr > td:nth-child(3)"));
        }

        public int getCorrectAnswerCount() {
            return contentParseInt(getContent("#problem-info > tbody > tr > td:nth-child(4)"));
        }

        public int getCorrectUserCount() {
            return contentParseInt(getContent("#problem-info > tbody > tr > td:nth-child(5)"));
        }

        public Double getCorrectRate() {
            return contentParseDouble(getContent("#problem-info > tbody > tr > td:nth-child(6)"));
        }

        public String getProblemBody() {
            return webDriver.findElement(By.cssSelector("#problem_description")).getAttribute("innerHTML");
        }

        public String getProblemInput() {
            return getContent("#problem_input");
        }

        public String getProblemOutput() {
            return getContent("#problem_output");
        }

        public String getSampleInput() {
            return getContent("#sample-input-1");
        }

        public String getSampleOutput() {
            return getContent("#sample-output-1");
        }
    }

    public class ProblemList {
        public ProblemList(Integer algoCode, Integer page) {
            String url = String.format(
                    "https://www.acmicpc.net/problemset?sort=ac_desc&algo=%d&algo_if=and&page=%d",
                    algoCode,
                    page);

            if(webDriver.getCurrentUrl().equals(url))
                return;

            webDriver.get(url);
        }

        public List<Integer> getProblemPages() {
            return webDriver.findElement(By.cssSelector("body > div.wrapper > div.container.content > div:nth-child(6) > div:nth-child(2) > div > ul"))
                    .findElements(By.tagName("li"))
                    .stream()
                    .map(WebElement::getText)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        }

        public List<Integer> getProblemNumbers() {
            return webDriver.findElement(By.cssSelector("#problemset > tbody"))
                    .findElements(By.tagName("tr"))
                    .stream()
                    .flatMap(e -> Stream.of(e.getText().split(" ")).limit(1))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        }
    }

    private String getContent(String cssSelector) {
        return webDriver.findElement(By.cssSelector(cssSelector)).getText();
    }

    private double contentParseDouble(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find())
            return Double.parseDouble(matcher.group());
        throw new ConvertException();
    }

    private int contentParseInt(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find())
            return Integer.parseInt(matcher.group());
        throw new ConvertException();
    }
}
