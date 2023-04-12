package vision.cotegory.entity;

import lombok.Getter;

import java.util.*;

import static vision.cotegory.entity.Tag.*;
import static vision.cotegory.entity.Tag.UNION_FIND;

public class TagGroupConst {

//    private final Set<Tag> tags;
//
//    public Set<Tag> getTags() {
//        return tags;
//    }
//
//    private TagGroupConst(Set<Tag> group) {
//        this.tags = group;
//    }
//
//    private static final TagGroup GROUP_A = new TagGroup(Set.of(
//            GREEDY,
//            DP,
//            BRUTE_FORCE,
//            BINARY_SEARCH
//    ));
//    private static final TagGroup GROUP_B = new TagGroup(Set.of(
//            DFS,
//            BFS,
//            BRUTE_FORCE,
//            DP
//    ));
//    private static final TagGroup GROUP_C = new TagGroup(Set.of(
//            DIJKSTRA,
//            FLOYD_WARSHALL,
//            BIT_MASKING,
//            UNION_FIND
//    ));
//
//    static public Map<TagGroup, Tag> assignableGroups(Set<Tag> tags) {
//        var ret = new HashMap<TagGroup, Tag>();
//        List.of(GROUP_A, GROUP_B, GROUP_C)
//                .forEach(group -> {
//                    Set<Tag> intersectSet = intersect(group.getT, tags);
//                    if (intersectSet.size() == 1)
//                        ret.put(group, intersectSet.stream().findAny().get());
//                });
//        return ret;
//    }
//
//    static private Set<Tag> intersect(Set<Tag> a, Set<Tag> b) {
//        final HashSet<Tag> tmp = new HashSet<>(a);
//        tmp.retainAll(b);
//        return tmp;
//    }
//
//    @Override
//    public String toString() {
//        if(this.equals(GROUP_A))
//            return "GROUP_A";
//        if(this.equals(GROUP_B))
//            return "GROUP_B";
//        if(this.equals(GROUP_C))
//            return "GROUP_C";
//        return "UNDEFINED_GROUP";
//    }
}
