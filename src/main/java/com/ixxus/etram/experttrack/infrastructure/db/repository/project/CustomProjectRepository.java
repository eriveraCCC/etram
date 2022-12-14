/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.experttrack.infrastructure.db.repository.project;

import com.ixxus.etram.experttrack.model.entity.project.CustomProjectTocEntity;
import com.ixxus.etram.experttrack.model.entity.article.CustomArticleTopLevelEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomProjectRepository {


    private final EntityManager entityManager;

    private static final String TOP_ARTICLES_QUERY = "SELECT DISTINCT " +
            "parent.id_page as id_page_parent," +
            "parent.page_name as page_name_parent " +
            "FROM " +
            "tbl_collection " +
            "LEFT JOIN tbl_page as parent on " +
            "parent.id_page = tbl_collection.id_page " +
            "WHERE " +
            "parent.id_project = :idProject " +
            "ORDER BY " +
            "id_page_parent, toc_sequence";

    private static final String TOC_PROJECT_QUERY = "SELECT " +
            "tbl_page_toc.id_page, " +
            "tbl_page.page_name, " +
            "tbl_page_toc.id_page_toc, " +
            "tbl_page_toc.is_toc, " +
            "tbl_page_toc.toc_sequence " +
            "FROM " +
            "tbl_page_toc " +
            "JOIN tbl_page on " +
            "tbl_page.id_page = tbl_page_toc.id_page " +
            "WHERE " +
            "tbl_page.id_project = :idProject " +
            "ORDER BY " +
            "tbl_page_toc.toc_sequence";

    public List<CustomArticleTopLevelEntity> findTopArticlesProject(
            Integer idProject) {
        Query q = entityManager.createNativeQuery(TOP_ARTICLES_QUERY, CustomArticleTopLevelEntity.class);
        q.setParameter("idProject", idProject);

        return q.getResultList();
    }

    public List<CustomProjectTocEntity> findProjectToc(
            Integer idProject) {
        Query q = entityManager.createNativeQuery(TOC_PROJECT_QUERY, CustomProjectTocEntity.class);
        q.setParameter("idProject", idProject);

        return q.getResultList();
    }

}
