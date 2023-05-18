package vision.cotegory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vision.cotegory.entity.tag.Tag;
import vision.cotegory.entity.tag.TagGroup;
import vision.cotegory.repository.TagGroupRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TagGroupService {

    private final TagGroupRepository tagGroupRepository;

    public Map<TagGroup, Tag> assignableGroups(Set<Tag> tags) {
        var ret = new HashMap<TagGroup, Tag>();
        getTagGroupConsts().forEach(group -> {
            Set<Tag> intersectSet = intersect(group.getTags(), tags);
            if (intersectSet.size() == 1)
                ret.put(group, intersectSet.stream().findAny().get());
        });
        return ret;
    }

    public List<TagGroup> getTagGroupConsts(){
        return tagGroupRepository.findAll();
    }

    private Set<Tag> intersect(Set<Tag> a, Set<Tag> b) {
        final HashSet<Tag> tmp = new HashSet<>(a);
        tmp.retainAll(b);
        return tmp;
    }
}
