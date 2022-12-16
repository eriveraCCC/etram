/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.confluence.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Article {

    private String id;
    private String type;
    private String title;
    private Space space;
    private Body body;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Version version;
}
