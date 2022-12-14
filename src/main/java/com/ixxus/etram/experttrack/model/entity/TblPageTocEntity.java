/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.experttrack.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tbl_page_toc")
public class TblPageTocEntity {

    @Id
    @Column(name = "id_page_toc")
    private int idPageToc;

    @Column (name = "id_page")
    private int idPage;

    @Column (name = "toc_secuence")
    private int tocSecuence;

    @Column (name = "is_toc")
    private boolean isToc;

}
