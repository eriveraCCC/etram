/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Configuration
public class RestTemplateConfig {

    private static final String CONFLUENCE_USERNAME = "";
    private static final String CONFLUENCE_TOKEN = "";

    @Bean
    public RestTemplate restTemplate() {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(CONFLUENCE_USERNAME, CONFLUENCE_TOKEN));

        StringHttpMessageConverter messageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        restTemplate.getMessageConverters().add(messageConverter);

        return restTemplate;
    }
}
