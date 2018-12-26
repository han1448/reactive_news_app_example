package com.oppalove.reactive.demoapp.news.rest;

import com.oppalove.reactive.demoapp.tag.model.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.List;

@Slf4j
@Component
public class TagClient {
    WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8082")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
            .build();

    public Flux<Tag> callTagpi(List<String> ids) {
        return webClient.get()
                .uri(uriBuilder -> buildUri(ids, uriBuilder))
                .retrieve()
                .bodyToFlux(Tag.class);
    }

    private URI buildUri(List<String> ids, UriBuilder uriBuilder) {
        URI uri = uriBuilder.path("tag")
                .queryParam("tagIds", StringUtils.join(ids, ","))
                .build();
        log.debug("uri: {}", uri);
        return uri;
    }
}
