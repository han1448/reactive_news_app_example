package com.oppalove.reactive.demoapp.media.api;

import com.oppalove.reactive.demoapp.media.model.MediaRequest;
import com.oppalove.reactive.demoapp.media.model.Medium;
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
public class MediaController {

    private Map<String, Medium> mediumMap;

    public MediaController() {
        mediumMap = new HashMap<>();
        mediumMap.put("m1", Medium.builder()
                .id("m1")
                .imageUrl("http://localhost/img/victory.jpg")
                .name("victory")
                .build());
        mediumMap.put("m2", Medium.builder()
                .id("m2")
                .imageUrl("http://localhost/img/food.jpg")
                .name("food")
                .build());
        mediumMap.put("m3", Medium.builder()
                .id("m3")
                .imageUrl("http://localhost/img/car.jpg")
                .name("car")
                .build());
        mediumMap.put("m4", Medium.builder()
                .id("m4")
                .imageUrl("http://localhost/img/love.jpg")
                .name("love")
                .build());
    }

    public static void main(String[] args) {
        System.setProperty("server.port", "8081");
        SpringApplication.run(MediaController.class, args);
    }

    @RequestMapping("/media")
    public Flux<Medium> findMedia(MediaRequest request) {
        if (Optional.ofNullable(request)
                .map(MediaRequest::getMediaIds)
                .isPresent()) {
            List<Medium> media = request.getMediaIds().stream()
                    .filter(id -> mediumMap.containsKey(id))
                    .map(id -> mediumMap.get(id))
                    .collect(Collectors.toList());
            // delay for the test
            return Flux.fromIterable(media).delayElements(Duration.ofMillis(10));
        } else {
            return Flux.empty();
        }
    }
}
