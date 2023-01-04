/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.experttrack.infrastructure.db.repository.article;

import com.ixxus.etram.experttrack.model.entity.article.CustomArticleChildEntity;
import com.ixxus.etram.experttrack.model.entity.article.CustomArticleHtmlContentEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomArticleRepository {

    private final EntityManager entityManager;

    private static final String QUERY_CHILD_ARTICLES = "SELECT " +
            "parent.id_page as id_page_parent, " +
            "parent.page_name as page_name_parent, " +
            "child.id_page as id_page_child, " +
            "child.page_name as page_name_child, " +
            "toc_sequence " +
            "FROM " +
            "tbl_collection " +
            "JOIN tbl_page as parent on " +
            "parent.id_page = tbl_collection.id_page " +
            "JOIN tbl_page as child on " +
            "child.id_page = tbl_collection.id_page_in_collection "  +
            "WHERE " +
            "parent.id_page = :idPage " +
            "ORDER BY " +
            "id_page_parent, toc_sequence";

    private static final String QUERY_HTML_CONTENT = "SELECT " +
            "tbl_page.id_page as id_page, " +
            "id_content, " +
            "page_name, " +
            "content " +
            "FROM " +
            "expertrack.tbl_content " +
            "JOIN tbl_page ON " +
            "tbl_page.id_page = tbl_content.id_page " +
            "WHERE " +
            "tbl_page.id_page  = :idPage";

    private static final String QUERY_PAGE_NAME = "SELECT DISTINCT page_name " +
            "from tbl_page tp " +
            "where id_page = :idPage";

    public List<CustomArticleChildEntity> findChildArticles(
            Integer idPage) {
        Query q = entityManager.createNativeQuery(QUERY_CHILD_ARTICLES, CustomArticleChildEntity.class);
        q.setParameter("idPage", idPage);

        return q.getResultList();
    }

    public CustomArticleHtmlContentEntity getHtmlContent(
            Integer idPage) {
        Query q = entityManager.createNativeQuery(QUERY_HTML_CONTENT, CustomArticleHtmlContentEntity.class);
        q.setParameter("idPage", idPage);

        return (CustomArticleHtmlContentEntity) q.getSingleResult();
    }

    public String getPageName (
            Integer idPage) {
        Query q = entityManager.createNativeQuery(QUERY_PAGE_NAME, String.class);
        q.setParameter("idPage", idPage);

        return (String) q.getSingleResult();
    }

}
