/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.experttrack.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ArticleChild {

    private final Integer idPageParent;

    private final String pageNameParent;

    private final Integer idPageChild;

    private final String pageNameChild;

    private final Integer tocSequence;

}
