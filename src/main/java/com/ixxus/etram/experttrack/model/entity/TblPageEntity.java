/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.experttrack.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tbl_page")
public class TblPageEntity {

    @Id
    @Column(name = "id_page")
    private int idPage;

    @Column (name = "id_project")
    private int idProject;

    @Column (name = "id_language")
    private int idLanguage;

    @Column (name = "id_page_lk_type")
    private int idPageLkType;

    @Column (name = "page_name")
    private String content;

    @Column (name = "has_dms")
    private boolean hasDms;

    @Column (name = "has_collection")
    private boolean hasCollection;

    @Column (name = "deleted")
    private boolean deleted;

}
