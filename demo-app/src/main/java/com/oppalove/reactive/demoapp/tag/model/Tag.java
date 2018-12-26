package com.oppalove.reactive.demoapp.tag.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Tag {
    String id;
    String name;
}
