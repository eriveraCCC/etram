/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.experttrack.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectToc {

    private final Integer idPage;

    private final String pageName;

    private final Integer idPageToc;

    private final boolean isToc;

    private final Integer tocSequence;

}
