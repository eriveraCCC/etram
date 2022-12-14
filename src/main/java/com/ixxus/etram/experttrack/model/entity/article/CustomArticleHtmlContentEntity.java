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
public class CustomArticleHtmlContentEntity {

    @Id
    @Column(name = "id_page")
    private Integer idPage;

    @Id
    @Column (name = "id_content")
    private Integer idContent;

    @Column(name = "page_name")
    private String pageName;

    @Column (name = "content")
    private String content;

}
