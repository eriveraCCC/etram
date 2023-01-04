package com.ixxus.etram.migration.application.services.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReferencedPage {

    private String fullLink;

    private String newLink;

    private Integer projectId;

    private Integer pageId;

}
