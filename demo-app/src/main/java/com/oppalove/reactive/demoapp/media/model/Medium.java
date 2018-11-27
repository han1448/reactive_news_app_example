package com.oppalove.reactive.demoapp.media.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Medium {
    String id;
    String name;
    String imageUrl;
}
