package com.oppalove.reactive.demoapp.news.app;

import com.oppalove.reactive.demoapp.media.model.Medium;
import com.oppalove.reactive.demoapp.news.model.NewsFeed;
import com.oppalove.reactive.demoapp.news.model.NewsResponse;
import com.oppalove.reactive.demoapp.news.rest.MediaClient;
import com.oppalove.reactive.demoapp.news.rest.TagClient;
import com.oppalove.reactive.demoapp.tag.model.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.util.*;

@AllArgsConstructor
@Service
@Slf4j
public class NewsService {

    private static Map<String, NewsFeed> newsFeedMap = new HashMap<>();

    static {
        newsFeedMap.put("1", NewsFeed.builder()
                .id("news1")
                .title("New topic1")
                .mediaIds(Arrays.asList("m1", "m5"))
                .tagIds(Arrays.asList("t1", "t2"))
                .build());
        newsFeedMap.put("2", NewsFeed.builder()
                .id("news2")
                .title("New topic2")
                .mediaIds(Arrays.asList("m3", "m4"))
                .tagIds(Arrays.asList("t3", "t4"))
                .build());
        newsFeedMap.put("3", NewsFeed.builder()
                .id("news3")
                .title("New topic3")
                .tagIds(Arrays.asList("t3", "t4"))
                .build());
    }

    private final MediaClient mediaClient;
    private final TagClient tagClient;

    public Flux<NewsResponse> findNewsWithArray() {

        Flux<NewsFeed> feedFlux = Optional.of(newsFeedMap.values())
                .map(Flux::fromIterable)
                .orElse(Flux.empty());

        Flux<NewsResponse> newsResponseFlux = feedFlux.flatMap(newsFeed -> {

            List<Mono<?>> monoArrayList = new ArrayList<>();
            monoArrayList.add(mediaClient.callMediumApi(newsFeed.getMediaIds())
                    .collectList()
                    .defaultIfEmpty(Collections.emptyList()));
            monoArrayList.add(tagClient.callTagpi(newsFeed.getTagIds())
                    .collectList()
                    .defaultIfEmpty(Collections.emptyList()));

            return Mono.zip(monoArrayList, (Object[] arr) -> {
                int length = arr.length;
                log.debug("length of flux list : {}", length);

                List<Medium> media = (List<Medium>) arr[0];
                List<Tag> tags = (List<Tag>) arr[1];

                return NewsResponse.builder()
                        .id(newsFeed.getId())
                        .title(newsFeed.getTitle())
                        .media(media)
                        .tags(tags)
                        .build();
            });
        });
        return newsResponseFlux;
    }

    public Flux<NewsResponse> findNewsZipWith() {
        Flux<NewsFeed> feedFlux = Optional.of(newsFeedMap.values())
                .map(Flux::fromIterable)
                .orElse(Flux.empty());

        Flux<NewsResponse> newsResponseFlux = feedFlux.flatMap(newsFeed -> Mono.just(newsFeed)
                .zipWith(mediaClient.callMediumApi(newsFeed.getMediaIds())
                        .collectList()
                        .defaultIfEmpty(Collections.emptyList()), (Tuples::of))
                .zipWith(tagClient.callTagpi(newsFeed.getTagIds())
                                .collectList()
                                .defaultIfEmpty(Collections.emptyList()),
                        ((tuple, tag) -> Tuples.of(tuple.getT1(), tuple.getT2(), tag)))
                .map(res -> NewsResponse.builder()
                        .id(res.getT1().getId())
                        .title(res.getT1().getTitle())
                        .media(res.getT2())
                        .tags(res.getT3())
                        .build()))
                .sort(Comparator.comparing(NewsResponse::getId));
        return newsResponseFlux;
    }

}
