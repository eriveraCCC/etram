/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.confluence.infrastructure.rest.out.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConfluenceRestResponse {

    private String id;
    private String type;
    private String status;
    private String title;

    private ConfluenceRestVersionResponse version;
    private ConfluenceRestBodyResponse body;
    @JsonProperty("_links")
    private ConfluenceLinksBodyResponse links;

}
