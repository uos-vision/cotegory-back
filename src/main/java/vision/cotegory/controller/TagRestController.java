package vision.cotegory.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vision.cotegory.controller.response.TagGroupResponse;
import vision.cotegory.controller.response.TagGroupsResponse;
import vision.cotegory.controller.response.TagsResponse;
import vision.cotegory.entity.tag.Tag;
import vision.cotegory.repository.TagGroupRepository;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tag")
@Transactional
public class TagRestController {

    private final TagGroupRepository tagGroupRepository;

    @Operation(description = "인증토큰 없이 호출 가능")
    @GetMapping("/list")
    public ResponseEntity<TagsResponse> tags() {
        return ResponseEntity.ok(TagsResponse.builder()
                .tags(Tag.valuesWithoutOthers())
                .build());
    }

    @Operation(description = "인증토큰 없이 호출 가능")
    @GetMapping("/groups")
    public ResponseEntity<TagGroupsResponse> tagGroups() {
        return ResponseEntity.ok(TagGroupsResponse.builder()
                .tagGroups(tagGroupRepository.findAll()
                        .stream()
                        .map(TagGroupResponse::new)
                        .collect(Collectors.toList()))
                .build());
    }
}
