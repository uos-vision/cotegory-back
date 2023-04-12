package vision.cotegory.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vision.cotegory.repository.TagGroupRepository;

import java.util.*;

import static vision.cotegory.entity.Tag.*;
import static vision.cotegory.entity.Tag.UNION_FIND;

@Component
@Transactional
public class TagGroupConst {

    private final TagGroupRepository tagGroupRepository;
    private final List<TagGroup> tagGroups = new ArrayList<>();

    @Autowired
    public TagGroupConst(TagGroupRepository tagGroupRepository) {
        this.tagGroupRepository = tagGroupRepository;
        saveConst();
    }

    private void saveConst(){
        final TagGroup groupA = TagGroup.builder().name("groupA").tags(Set.of(
                GREEDY,
                DP,
                BRUTE_FORCE,
                BINARY_SEARCH
        )).build();
        tagGroups.add(groupA);
        final TagGroup groupB = TagGroup.builder().name("groupB").tags(Set.of(
                DFS,
                BFS,
                BRUTE_FORCE,
                DP
        )).build();
        tagGroups.add(groupB);
        final TagGroup groupC = TagGroup.builder().name("groupC").tags(Set.of(
                DIJKSTRA,
                FLOYD_WARSHALL,
                BIT_MASKING,
                UNION_FIND
        )).build();
        tagGroups.add(groupC);
        tagGroupRepository.saveAll(tagGroups);
    }

    public Map<TagGroup, Tag> assignableGroups(Set<Tag> tags) {
        var ret = new HashMap<TagGroup, Tag>();
        tagGroups.forEach(group -> {
            Set<Tag> intersectSet = intersect(group.getTags(), tags);
            if (intersectSet.size() == 1)
                ret.put(group, intersectSet.stream().findAny().get());
        });
        return ret;
    }

    private Set<Tag> intersect(Set<Tag> a, Set<Tag> b) {
        final HashSet<Tag> tmp = new HashSet<>(a);
        tmp.retainAll(b);
        return tmp;
    }
}
