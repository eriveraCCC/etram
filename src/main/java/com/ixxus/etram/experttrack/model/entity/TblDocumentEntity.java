/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.experttrack.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tbl_document")
public class TblDocumentEntity {

    @Id
    @Column(name = "id_document")
    private int idDocument;

    @Column (name = "id_project")
    private int idProject;

    @Column (name = "id_language")
    private int idLanguage;

    @Column (name = "id_doc_lk_type")
    private int idDocLkType;

    @Column(name = "filename")
    private String filename;

    @Column (name = "deleted")
    private boolean deleted;

    @Column (name = "description")
    private String description;

    @Column (name = "filesize")
    private int filesize;

    @Column(name = "absolute_url")
    private String absoluteUrl;

    @Column (name = "dms_type")
    private int dmsType;

    @Column (name = "is_public")
    private boolean isPublic;

}
