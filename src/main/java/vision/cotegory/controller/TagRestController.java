package vision.cotegory.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vision.cotegory.controller.response.TagGroupResponse;
import vision.cotegory.controller.response.TagGroupsResponse;
import vision.cotegory.controller.response.TagsResponse;
import vision.cotegory.entity.Tag;
import vision.cotegory.entity.TagGroup;
import vision.cotegory.repository.TagGroupRepository;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tag")
@Transactional
public class TagRestController {

    private final TagGroupRepository tagGroupRepository;

    @Operation(summary = "인증토큰 없이 호출 가능")
    @GetMapping("/list")
    public ResponseEntity<TagsResponse> tags() {
        return ResponseEntity.ok(TagsResponse.builder()
                .tags(Stream.of(Tag.values()).collect(Collectors.toList()))
                .build());
    }

    @Operation(summary = "인증토큰 없이 호출 가능")
    @GetMapping("/groups")
    public ResponseEntity<TagGroupsResponse> tagGroups() {
        return ResponseEntity.ok(TagGroupsResponse.builder()
                .tagGroups(tagGroupRepository
                        .findAll()
                        .stream()
                        .map(TagGroupResponse::new)
                        .collect(Collectors.toList()))
                .build());
    }
}
