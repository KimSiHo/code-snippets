/*
 * Copyright 2021 Etoos Education. All rights Reserved.
 * Etoos PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package me.bigmonkey.structure.common.httpclient;

import java.nio.charset.StandardCharsets;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import me.bigmonkey.structure.common.httpclient.properties.HttpClientProperties;

@Configuration
public class RestTemplateConfig {

    @Autowired
    private HttpClientProperties properties;

    @Bean
    public RestTemplate restTemplate() {
        RequestConfig config = RequestConfig.custom()
            .setConnectTimeout((int) properties.getConnectTimeout().toMillis())
            .setSocketTimeout((int) properties.getSocketTimeout().toMillis())
            .setConnectionRequestTimeout((int) properties.getConnectRequestTimeout().toMillis()).build();

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        if (properties.getMaxConnect() != 0 && properties.getMaxConnectPerRoute() != 0) {
            httpClientBuilder.setMaxConnTotal(1).setMaxConnPerRoute(1);
        }
        CloseableHttpClient client = httpClientBuilder.setDefaultRequestConfig(config).build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(client);
        factory.setReadTimeout((int) properties.getReadTimeout().toMillis());

        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        return restTemplate;
    }
}
