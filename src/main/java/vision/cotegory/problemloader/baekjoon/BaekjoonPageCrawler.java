package vision.cotegory.problemloader.baekjoon;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class BaekjoonPageCrawler {

    private final Document doc;
    private String url;

    public BaekjoonPageCrawler(Integer problemNumber) {
        url = String.format("https://www.acmicpc.net/problem/%d", problemNumber);
        try {
            this.doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUrl(){
        return this.url;
    }

    public String getContent(String cssSelector) {
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

    public String getProblemBody() {
        return convertRelativeImgPathToAbsoluteImgPath(doc.select("#problem_description").html());
    }

    public String getProblemInput() {
        return convertRelativeImgPathToAbsoluteImgPath(doc.select("#problem_input").html());
    }

    public String getProblemOutput() {
        return convertRelativeImgPathToAbsoluteImgPath(doc.select("#problem_output").html());
    }

    public String getSampleInput() {
        return convertRelativeImgPathToAbsoluteImgPath(doc.select("#sample-input-1").html());
    }

    public String getSampleOutput() {
        return convertRelativeImgPathToAbsoluteImgPath(doc.select("#sample-output-1").html());
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

    private double contentParseDouble(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find())
            return Double.parseDouble(matcher.group());
        return 0.0;
    }

    private int contentParseInt(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find())
            return Integer.parseInt(matcher.group());
        return 0;
    }

    private String convertRelativeImgPathToAbsoluteImgPath(String html){
        final String tag = "img";
        final String attr = "src";
        final String baseUrl = "https://www.acmicpc.net";

        Document document = Jsoup.parse(html, "https://www.acmicpc.net");
        Elements elements = document.getElementsByTag(tag);

        for(var element :elements){
            String path = element.getElementsByTag(tag).attr(attr);
            if(!path.startsWith("/"))
                continue;
            element.getElementsByTag(tag).attr(attr, baseUrl + path);
        }

        return document.html();
    }
}