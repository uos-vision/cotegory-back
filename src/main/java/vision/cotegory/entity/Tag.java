package vision.cotegory.entity;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

@Slf4j
public enum Tag {
    DP("다이나믹 프로그래밍", 25),
    DFS("깊이 우선 탐색", 127),
    BFS("너비 우선 탐색", 126),
    BRUTE_FORCE("브루트포스 알고리즘", 125),
    GREEDY("그리디 알고리즘", 33),
    BINARY_SEARCH("이분탐색", 12),
    BIT_MASKING("비트마스킹", 14),
    DIJKSTRA("데이크스트라", 22),
    FLOYD_WARSHALL("플로이드-워셜", 31),
    UNION_FIND("분리 집합", 81),
    OTHERS("기타등등", -42);


    private final String korean;
    private final Integer baekjoonCode;

    Tag(String korean, Integer baekjoonCode) {
        this.korean = korean;
        this.baekjoonCode = baekjoonCode;
    }

    public String toKorean() {
        return this.korean;
    }

    public Integer toBaekjoonCode() {
        return this.baekjoonCode;
    }

    public static Tag of(String korean) {
        return Stream.of(values())
                .filter(e -> e.toKorean().equals(korean))
                .findAny()
                .orElse(OTHERS);
    }
}
