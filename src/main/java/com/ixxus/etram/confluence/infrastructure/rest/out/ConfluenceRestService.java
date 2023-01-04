/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.confluence.infrastructure.rest.out;

import com.ixxus.etram.confluence.infrastructure.rest.out.response.ConfluenceRestResponse;
import com.ixxus.etram.confluence.infrastructure.rest.out.response.ConfluenceRestResponseList;
import com.ixxus.etram.confluence.model.Article;
import com.ixxus.etram.confluence.model.ChildArticle;
import com.ixxus.etram.confluence.model.Space;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Component
@Slf4j
public class ConfluenceRestService {

    private static final String IXXUS_CONFLUENCE_GET_ARTICLE_URL = "https://ixxuswiki.atlassian.net/wiki/rest/api/content/{articleId}?expand=body.storage";
    private static final String IXXUS_CONFLUENCE_GET_ALL_ARTICLES_URL = "https://ixxuswiki.atlassian.net/wiki/rest/api/content?spaceKey={spaceId}&expand=version,body.storage&limit=200";
    private static final String IXXUS_CONFLUENCE_CREATE_ARTICLE_URL = "https://ixxuswiki.atlassian.net/wiki/rest/api/content";
    private static final String IXXUS_CONFLUENCE_UPDATE_ARTICLE_URL = "https://ixxuswiki.atlassian.net/wiki/rest/api/content/{articleId}";
    private static final String IXXUS_CONFLUENCE_UPLOAD_ATTACHMENTS_URL = "https://ixxuswiki.atlassian.net/wiki/rest/api/content/{articleId}/child/attachment";
    private static final String IXXUS_CONFLUENCE_CREATE_SPACE_URL = "https://ixxuswiki.atlassian.net/wiki/rest/api/space";

    private final RestTemplate restTemplate;

    public ConfluenceRestResponse getArticleContent(String articleId) {

        Map<String, String> params = new HashMap<>();
        params.put("articleId", articleId);

        ResponseEntity<ConfluenceRestResponse> response = restTemplate.exchange(IXXUS_CONFLUENCE_GET_ARTICLE_URL, HttpMethod.GET, new HttpEntity<>(setHeaders()), ConfluenceRestResponse.class, params);
        ConfluenceRestResponse articleRestResponse = response.getBody();
        return articleRestResponse;
    }

    public ConfluenceRestResponseList getAllArticles(String spaceId) {

        Map<String, String> params = new HashMap<>();
        params.put("spaceId", spaceId);

        ResponseEntity<ConfluenceRestResponseList> response = restTemplate.exchange(IXXUS_CONFLUENCE_GET_ALL_ARTICLES_URL, HttpMethod.GET, new HttpEntity<>(setHeaders()), ConfluenceRestResponseList.class, params);
        ConfluenceRestResponseList articlesListResponse = response.getBody();
        return articlesListResponse;
    }

    public ConfluenceRestResponse createSpace(Space space) {

        ResponseEntity<ConfluenceRestResponse> response = restTemplate.exchange(IXXUS_CONFLUENCE_CREATE_SPACE_URL, HttpMethod.POST, new HttpEntity<>(space, setHeaders()), ConfluenceRestResponse.class);
        ConfluenceRestResponse articleRestResponse = response.getBody();
        return articleRestResponse;
    }
    public ConfluenceRestResponse createArticle(Article article) {

        parseContent(article);
        replaceJSLinks(article);
        ResponseEntity<ConfluenceRestResponse> response = restTemplate.exchange(IXXUS_CONFLUENCE_CREATE_ARTICLE_URL, HttpMethod.POST, new HttpEntity<>(article, setHeaders()), ConfluenceRestResponse.class);
        ConfluenceRestResponse articleRestResponse = response.getBody();
        return articleRestResponse;
    }

    public ConfluenceRestResponse createChildArticle(ChildArticle childArticle) {


        parseContent(childArticle);
        replaceJSLinks(childArticle);
        ResponseEntity<ConfluenceRestResponse> response = restTemplate.exchange(IXXUS_CONFLUENCE_CREATE_ARTICLE_URL, HttpMethod.POST, new HttpEntity<>(childArticle, setHeaders()), ConfluenceRestResponse.class);
        ConfluenceRestResponse articleRestResponse = response.getBody();
        return articleRestResponse;
    }

    public ConfluenceRestResponse updateArticle(Article article) {

        parseContent(article);

        Map<String, String> params = new HashMap<>();
        params.put("articleId", article.getId());

        ResponseEntity<ConfluenceRestResponse> response = restTemplate.exchange(IXXUS_CONFLUENCE_UPDATE_ARTICLE_URL, HttpMethod.PUT, new HttpEntity<>(article, setHeaders()), ConfluenceRestResponse.class, params);
        ConfluenceRestResponse articleRestResponse = response.getBody();
        return articleRestResponse;
    }

    public ConfluenceRestResponseList uploadAttachments(String articleId, MultipartFile file, String comment) throws IOException {

        Map<String, String> params = new HashMap<>();
        params.put("articleId", articleId);

        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("file", getMultipartFileBytes(file));
        formData.add("comment", comment);

        ResponseEntity<ConfluenceRestResponseList> response = restTemplate.exchange(IXXUS_CONFLUENCE_UPLOAD_ATTACHMENTS_URL, HttpMethod.POST, new HttpEntity<>(formData, setHeaders()), ConfluenceRestResponseList.class, params);
        ConfluenceRestResponseList attachmentsRestResponse = response.getBody();

        return attachmentsRestResponse;
    }

    private ByteArrayResource getMultipartFileBytes (MultipartFile file) throws IOException {
        byte[] fileContent = file.getBytes();
        ByteArrayResource resource = new ByteArrayResource(fileContent) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };
        return resource;
    }

    private HttpHeaders setHeaders () {

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Atlassian-Token", "no-check");

        return headers;

    }

    private void parseContent(Article article) {
        var content = article.getBody().getStorage().getValue();
        var parse1 = content.replace("\"", "'");
        var parse2 = parse1.replace("\n", "");
        article.getBody().getStorage().setValue(parse2);
    }

    private void replaceJSLinks(Article article) {
        log.info("Replacing on article: {} ", article.getTitle() );

        var content = article.getBody().getStorage().getValue();

        Pattern pattern = Pattern.compile("href='javascript.*?'");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            String href = matcher.group(0);

            Pattern numberPattern = Pattern.compile("href='javascript:LoadWikiPage\\(&#39;(\\d+)&#39;, (\\d+)\\)'");
            Matcher numberMatcher = numberPattern.matcher(href);

            while (numberMatcher.find()) {
                String articleId = numberMatcher.group(1);
                String projectId = numberMatcher.group(2);

                String newLink = "href='https://intranet.deepsearchnine.com/ExperTrack/et.htm?_flowId=wiki-flow&amp;selectedIdProject=" + projectId + "&amp;idPage=" + articleId + "'";
                article.getBody().getStorage().setValue(content.replace(href, newLink));
            }
        }
    }

}
