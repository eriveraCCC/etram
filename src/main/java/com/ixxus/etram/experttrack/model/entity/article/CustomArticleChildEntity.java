/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.experttrack.model.entity.article;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class CustomArticleChildEntity {


    @Id
    @Column(name = "id_page_parent")
    private Integer idPageParent;

    @Column (name = "page_name_parent")
    private String pageNameParent;

    @Id
    @Column(name = "id_page_child")
    private Integer idPageChild;

    @Column (name = "page_name_child")
    private String pageNameChild;

    @Column (name = "toc_sequence")
    private Integer tocSequence;

}
