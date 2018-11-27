package com.oppalove.reactive.demoapp.tag.model;

import lombok.Data;

import java.util.List;

@Data
public class TagRequest {
    private List<String> tagIds;
}
