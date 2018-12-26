package com.oppalove.reactive.demoapp.news.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NewsFeed {
    String id;
    String title;
    List<String> mediaIds;
    List<String> tagIds;
}
