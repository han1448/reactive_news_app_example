package com.oppalove.reactive.demoapp.news.app;

import com.oppalove.reactive.demoapp.news.model.NewsResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@AllArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @RequestMapping(value = "/news")
    public Flux<NewsResponse> getNews() {
        return newsService.findNewsZipWith();
    }
}
