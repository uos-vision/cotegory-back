package vision.cotegory.problemloader.baekjoon;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import vision.cotegory.problemloader.baekjoon.dto.BaekjoonProblemMetaDto;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class BaekjoonPageListCrawler {
    private final Document doc;

    public BaekjoonPageListCrawler(Integer algoCode, Integer page) {
        String url = String.format(
                "https://www.acmicpc.net/problemset?sort=ac_desc&algo=%d&algo_if=and&page=%d",
                algoCode,
                page);
        this.doc = getDocument(url);
    }

    public BaekjoonPageListCrawler(Integer algoCode) {
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

    public List<Integer> getProblemPageNumbers() {
        return doc.select("body > div.wrapper > div.container.content > div:nth-child(6) > div:nth-child(2) > div > ul")
                .stream()
                .map(e -> e.getElementsByTag("li"))
                .flatMap(e -> Stream.of(e.text().split(" ")))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public List<BaekjoonProblemMetaDto> getProblemMetas() {
        return doc.select("#problemset > tbody")
                .select("tr")
                .stream()
                .map(e -> e.select("td"))
                .map(e -> e.stream().map(Element::text).limit(2).collect(Collectors.toList()))
                .map(e -> new BaekjoonProblemMetaDto(Integer.parseInt(e.get(0)), e.get(1)))
                .collect(Collectors.toList());
    }
}
