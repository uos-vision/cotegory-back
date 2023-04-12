package vision.cotegory.parser.baekjoon;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import vision.cotegory.exception.exception.ConvertException;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaekjoonProblemParser {

    private final Document doc;

    public BaekjoonProblemParser(Integer problemNumber){
        String url = String.format("https://www.acmicpc.net/problem/%d", problemNumber);
        try {
            this.doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getContent(String cssSelector){
        return doc.select(cssSelector).text();
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
        return doc.select("#problem_description").html();
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