/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.experttrack.application.services;

import com.ixxus.etram.confluence.application.services.ConfluenceService;
import com.ixxus.etram.confluence.model.Article;
import com.ixxus.etram.confluence.model.Body;
import com.ixxus.etram.confluence.model.Space;
import com.ixxus.etram.confluence.model.Storage;
import com.ixxus.etram.experttrack.infrastructure.db.repository.article.CustomArticleRepository;
import com.ixxus.etram.experttrack.model.ArticleChild;
import com.ixxus.etram.experttrack.model.ArticleHtmlContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final CustomArticleRepository articleRepository;

    private final ConfluenceService confluenceService;

    private static final String CONFLUENCE_SPACE = "~6374ea69a593cb822e92c7da";

    public List<ArticleChild> getChildArticles(Integer articleId) {

        var customChildArticleEntityList = articleRepository.findChildArticles(articleId);

        return customChildArticleEntityList.stream().map(x -> ArticleChild.builder()
                .idPageParent(x.getIdPageParent())
                .pageNameParent(x.getPageNameParent())
                .idPageChild(x.getIdPageChild())
                .pageNameChild(x.getPageNameChild())
                .tocSequence(x.getTocSequence())
                .build()).toList();
    }

    public String getHtmlContentAndCreatePage(Integer articleId) {

        var content = getHTMLContent(articleId);

        return createPage(content);

    }

    public ArticleHtmlContent getHTMLContent(Integer articleId) {

        var customArticleHtmlContentEntity = articleRepository.getHtmlContent(articleId);

        return ArticleHtmlContent.builder()
                .idPage(customArticleHtmlContentEntity.getIdPage())
                .idContent(customArticleHtmlContentEntity.getIdContent())
                .pageName(customArticleHtmlContentEntity.getPageName())
                .content(customArticleHtmlContentEntity.getContent())
                .build();
    }

    private String createPage(ArticleHtmlContent content) {

        var article = Article.builder()
                .type("page")
                .title(content.getPageName())
                .space(Space.builder()
                        .key(CONFLUENCE_SPACE)
                        .build())
                .body(Body.builder()
                        .storage(Storage.builder()
                                .value(content.getContent())
                                .representation("storage")
                                .build())
                        .build())
                .build();

        var response = confluenceService.createArticle(article);

        return response.getId();

    }

    public byte[] getImagesFromArticle(Integer articleId) throws Exception {

        var customArticleHtmlContentEntity = articleRepository.getHtmlContent(articleId);

        String content = removeBase64Padding(customArticleHtmlContentEntity.getContent());

        List<BufferedImage> images = extractImagesFromContent(content);

        return compressImages(images);
    }

    private List<BufferedImage> extractImagesFromContent(String content) {

        Pattern p = Pattern.compile("data:image/[a-zA-Z]+;base64,(?<match>[a-zA-Z0-9+/]+)");

        Matcher matcher = p.matcher(content);

        List<BufferedImage> images = new ArrayList<>();

        while (matcher.find()) {
            // Had to do the substring because I'm not able to erase the comma in the regex
            byte[] imageBytes = Base64.getDecoder().decode(matcher.group("match"));
            ByteArrayInputStream is = new ByteArrayInputStream(imageBytes);
            try {
                BufferedImage image = ImageIO.read(is);
                images.add(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally{
                IOUtils.closeQuietly(is);
            }
        }

        return images;

    }

    private byte[] compressImages(List<BufferedImage> images) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);

        try {
            for (BufferedImage image : images) {

                ZipEntry entry = new ZipEntry(String.format("image%d.png", images.indexOf(image)));
                zos.putNextEntry(entry);
                ImageIO.write(image, "png", zos);
                zos.closeEntry();
            }
        } finally {
            zos.close();
        }

        return baos.toByteArray();
    }

    private String removeBase64Padding(String content) {

        content = content.replace("\\", "");
        //content = content.replace("=", "");

        return content;
    }
}

