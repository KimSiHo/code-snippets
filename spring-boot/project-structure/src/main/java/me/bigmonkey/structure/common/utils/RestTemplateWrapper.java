package me.bigmonkey.structure.common.utils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.etoos.member.common.exception.MemberErrorCode;
import com.etoos.member.common.exception.MemberServiceException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * RestTemplate Wrapper class
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RestTemplateWrapper {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public HttpHeaders getDefaultApiHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    public <T> HttpEntity<T> createJsonApiEntity(T body) {
        final HttpHeaders headers = getDefaultApiHeaders();
        return new HttpEntity<>(body, headers);
    }

    public <T> HttpEntity<T> createJsonApiEntity(T body, HttpHeaders httpHeaders) {
        return new HttpEntity<>(body, httpHeaders);
    }

    public <T, R> T getForEntity(Class<T> clazz, String url, R body) {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, body);
        return convertValue(response, clazz);
    }

    public <T, R> T postForEntity(Class<T> clazz, String url, R body) {
        HttpEntity<R> request = null;
        ResponseEntity<String> response = null;
        JavaType javaType = null;
        try {
            request = createJsonApiEntity(body);
            response = restTemplate.postForEntity(url, request, String.class);
        } catch (Exception ex) {
            handleRestClientException(url, ex);
        }
        return convertValue(response, clazz);
    }

    public <T, R> T postForEntity(Class<T> clazz, String url, R body, HttpHeaders httpHeaders) {
        HttpEntity<R> request = null;
        ResponseEntity<String> response = null;
        try {
            request = createJsonApiEntity(body, httpHeaders);
            response = restTemplate.postForEntity(url, request, String.class);
        } catch (Exception ex) {
            handleRestClientException(url, ex);
        }
        return convertValue(response, clazz);
    }

    private <T> T convertValue(ResponseEntity<String> response, Class<T> tClass) {
        JavaType javaType = getJavaType(tClass);
        T result = null;
        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                result = objectMapper.readValue(response.getBody(), javaType);
            } catch (IOException e) {
                log.info(e.getMessage());
                throw new MemberServiceException(MemberErrorCode.UNKNOWN_SERVER_ERROR);
            }
        } else {
            throw new MemberServiceException(MemberErrorCode.UNKNOWN_SERVER_ERROR);
        }
        return result;
    }

    public <T> T convertValue(Object value, Class<T> clazz) {
        return objectMapper.convertValue(value, clazz);
    }

    private <T> JavaType getJavaType(Class<T> clazz) {
        return objectMapper.getTypeFactory().constructType(clazz);
    }

    private void handleRestClientException(String url, Exception ex) {
        log.info(MessageFormat.format("HttpClientErrorException Request {0}. (message >>> {1})", url, ex.getMessage()),
                ex);
        throw new MemberServiceException(MemberErrorCode.UNKNOWN_SERVER_ERROR,
                MessageFormat.format("Exception Occurred {0}. (message >>> {1})", url, ex.getMessage()), ex);
    }

}
