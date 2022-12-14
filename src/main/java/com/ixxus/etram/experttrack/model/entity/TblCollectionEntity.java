/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.experttrack.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tbl_collection")
public class TblCollectionEntity {

    @Id
    @Column(name = "id_collection")
    private Integer idCollection;

    @Column (name = "id_page")
    private Integer idPage;

    @Column (name = "id_page_in_colllection")
    private Integer idPageInCollection;

    @Column (name = "toc_sequence")
    private Integer tocSequence;

}
