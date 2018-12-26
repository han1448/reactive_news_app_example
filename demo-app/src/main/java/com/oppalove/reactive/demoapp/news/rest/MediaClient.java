package com.oppalove.reactive.demoapp.news.rest;

import com.oppalove.reactive.demoapp.media.model.Medium;
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
public class MediaClient {

    WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8081")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
            .build();

    public Flux<Medium> callMediumApi(List<String> ids) {
        return webClient.get()
                .uri(uriBuilder -> buildUri(ids, uriBuilder))
                .retrieve()
                .bodyToFlux(Medium.class);
    }

    private URI buildUri(List<String> ids, UriBuilder uriBuilder) {
        URI uri = uriBuilder.path("media")
                .queryParam("mediaIds", StringUtils.join(ids, ","))
                .build();
        log.debug("uri: {}", uri);
        return uri;
    }
}
