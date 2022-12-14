/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.experttrack.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity(name = "tbl_content")
public class TblContentEntity {

    @Id
    @Column (name = "id_content")
    private int idContent;

    @Column (name = "id_page")
    private int idPage;

    @Column (name = "id_user")
    private int idUser;

    @Column (name = "id_content_lk_publish")
    private int idContentLkPublish;

    @Column (name = "content")
    private String content;

    @Column (name = "publication_date")
    private Timestamp publicationDate;

    @Column (name = "expiration_date")
    private Timestamp expirationDate;

    @Column (name = "creation_date")
    private Timestamp creationDate;


}
