package com.oppalove.reactive.demoapp.tag.api;

import com.oppalove.reactive.demoapp.tag.model.Tag;
import com.oppalove.reactive.demoapp.tag.model.TagRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@EnableAutoConfiguration
public class TagController {

    private Map<String, Tag> tagMap;

    public TagController() {
        tagMap = new HashMap<>();
        tagMap.put("t1", Tag.builder()
                .id("t1")
                .name("sports")
                .build());
        tagMap.put("t2", Tag.builder()
                .id("t2")
                .name("person")
                .build());
        tagMap.put("t3", Tag.builder()
                .id("t3")
                .name("IT")
                .build());
        tagMap.put("t4", Tag.builder()
                .id("t4")
                .name("social")
                .build());
    }

    public static void main(String[] args) {
        System.setProperty("server.port", "8082");
        SpringApplication.run(TagController.class, args);
    }

    @RequestMapping("/tag")
    public Flux<Tag> findTag(TagRequest request) {
        if (Optional.ofNullable(request)
                .map(TagRequest::getTagIds)
                .isPresent()) {
            List<Tag> tags = request.getTagIds().stream()
                    .filter(id -> tagMap.containsKey(id))
                    .map(id -> tagMap.get(id))
                    .collect(Collectors.toList());
            return Flux.fromIterable(tags).delayElements(Duration.ofMillis(10));
        } else {
            return Flux.empty();
        }
    }
}
