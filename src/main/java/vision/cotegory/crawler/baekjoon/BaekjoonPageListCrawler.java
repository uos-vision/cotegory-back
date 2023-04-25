package vision.cotegory.crawler.baekjoon;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class BaekjoonPageListCrawler {
    private final Document doc;

    public BaekjoonPageListCrawler(Integer algoCode, Integer page){
        String url = String.format(
                "https://www.acmicpc.net/problemset?sort=ac_desc&algo=%d&algo_if=and&page=%d",
                algoCode,
                page);
        this.doc = getDocument(url);
    }

    public BaekjoonPageListCrawler(Integer algoCode){
        String url = String.format(
                "https://www.acmicpc.net/problemset?sort=ac_desc&algo=%d&algo_if=and&page=%d",
                algoCode,
                1);
        this.doc = getDocument(url);
    }

    private Document getDocument(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Integer> getProblemPages() {
        return doc.select("body > div.wrapper > div.container.content > div:nth-child(6) > div:nth-child(2) > div > ul")
                .stream()
                .map(e -> e.getElementsByTag("li"))
                .flatMap(e -> Stream.of(e.text().split(" ")))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public List<Integer> getProblemNumbers() {
        return doc.select("#problemset > tbody")
                .select("tr")
                .stream()
                .flatMap(e -> Stream.of(e.text().split(" ")).limit(1))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
