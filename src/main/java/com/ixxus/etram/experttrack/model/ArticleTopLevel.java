/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.experttrack.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ArticleTopLevel {

    private final Integer idPageParent;

    private final String pageName;

}
