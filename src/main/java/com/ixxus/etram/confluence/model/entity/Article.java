package com.ixxus.etram.confluence.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@Builder
@SuperBuilder
public class Article {
    private String type;
    private String title;
    private Space space;
    private Body body;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Version version;
}
