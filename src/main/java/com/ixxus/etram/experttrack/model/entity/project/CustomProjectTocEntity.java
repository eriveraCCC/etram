/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.experttrack.model.entity.project;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class CustomProjectTocEntity {

    @Id
    @Column(name = "id_page")
    private Integer idPage;

    @Column (name = "page_name")
    private String pageName;

    @Column(name = "id_page_toc")
    private Integer idPageToc;

    @Column(name = "is_toc")
    private boolean isToc;

    @Column(name = "toc_sequence")
    private Integer tocSequence;

}
