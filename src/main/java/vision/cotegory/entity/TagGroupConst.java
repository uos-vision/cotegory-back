package vision.cotegory.entity;

import lombok.Getter;

import java.util.*;

import static vision.cotegory.entity.Tag.*;
import static vision.cotegory.entity.Tag.UNION_FIND;

public class TagGroupConst {

    @Getter
    private static final List<TagGroup> tagGroups = new ArrayList<>();

    private static final TagGroup GROUP_A = TagGroup.builder().tags(Set.of(
            GREEDY,
            DP,
            BRUTE_FORCE,
            BINARY_SEARCH
    )).build();
    private static final TagGroup GROUP_B = TagGroup.builder().tags(Set.of(
            DFS,
            BFS,
            BRUTE_FORCE,
            DP
    )).build();
    private static final TagGroup GROUP_C = TagGroup.builder().tags(Set.of(
            DIJKSTRA,
            FLOYD_WARSHALL,
            BIT_MASKING,
            UNION_FIND
    )).build();

    static {
        tagGroups.add(GROUP_A);
        tagGroups.add(GROUP_B);
        tagGroups.add(GROUP_C);
    }

    static public Map<TagGroup, Tag> assignableGroups(Set<Tag> tags) {
        var ret = new HashMap<TagGroup, Tag>();
        tagGroups.forEach(group -> {
            Set<Tag> intersectSet = intersect(group.getTags(), tags);
            if (intersectSet.size() == 1)
                ret.put(group, intersectSet.stream().findAny().get());
        });
        return ret;
    }

    static private Set<Tag> intersect(Set<Tag> a, Set<Tag> b) {
        final HashSet<Tag> tmp = new HashSet<>(a);
        tmp.retainAll(b);
        return tmp;
    }
}
