package com.ixxus.etram.confluence.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Body {

    @JsonProperty("body")
    private Content body;
}
