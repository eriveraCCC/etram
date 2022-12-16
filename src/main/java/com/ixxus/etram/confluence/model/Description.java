/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.confluence.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Description {

    @JsonProperty("plain")
    private Storage plain;
}
