package com.ixxus.etram.confluence.model.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Content {

    private String value;
    private String representation;
}
