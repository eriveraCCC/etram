/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.experttrack.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tbl_project")
public class TblProjectEntity {

    @Id
    @Column(name = "id_project")
    private int idProject;

    @Column (name = "project_name")
    private String projectName;

    @Column (name = "deleted")
    private boolean deleted;

    @Column (name = "id_client")
    private int idClient;

    @Column(name = "id_language")
    private int idLanguage;

    @Column (name = "id_content_lk_publish")
    private int idContentLkPublish;

    @Column (name = "is_template")
    private boolean isTemplate;

    @Column (name = "is_library")
    private boolean isLibrary;

    @Column(name = "has_public_site")
    private boolean hasPublicSite;

    @Column (name = "linear_wiki")
    private boolean linearWiki;

    @Column (name = "numbering_level")
    private int numberingLevel;

    @Column (name = "book_number")
    private String bookNumber;

}
