package com.ixxus.etram.experttrack.model.entity.article;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class CustomArticleEntity {

    @Id
    @Column(name = "id_page")
    private Integer idPage;

    @Column (name = "id_project")
    private Integer idProject;

    @Column(name = "page_name")
    private String pageName;

}
