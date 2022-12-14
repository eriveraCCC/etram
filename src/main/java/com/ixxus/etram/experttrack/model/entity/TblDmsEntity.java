/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.experttrack.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tbl_dms")
public class TblDmsEntity {

    @Id
    @Column(name = "id_dms")
    private int idDms;

    @Column (name = "id_page")
    private int idPage;

    @Column (name = "id_document")
    private int idDocument;

}
