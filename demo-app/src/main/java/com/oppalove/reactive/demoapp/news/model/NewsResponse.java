package com.oppalove.reactive.demoapp.news.model;

import com.oppalove.reactive.demoapp.media.model.Medium;
import com.oppalove.reactive.demoapp.tag.model.Tag;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NewsResponse {
    String id;
    String title;
    List<Medium> media;
    List<Tag> tags;
}
