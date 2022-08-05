package com.bigmonkey.jackson;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@SpringBootTest
class JacksonApplicationTests {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void contextLoads() throws JsonProcessingException {
        final CuriDormantWakeUpRequest build = CuriDormantWakeUpRequest.builder()
            .email("test@naver.com")
            .inactiveToken("abcd")
            .socialType(CuriSocialType.SOC001)
            .build();

        final String s = objectMapper.writeValueAsString(build);
        System.out.println("s = " + s);
    }

    @Builder
    @Data
    public static class CuriDormantWakeUpRequest {

        private String inactiveToken;

        private String email;

        private boolean social;

        private CuriSocialType socialType;

        private String uid;

    }

    @AllArgsConstructor
    public static enum CuriSocialType {
        SOC001("facebook"), SOC002("kakao"), SOC003("naver"), SOC004("google"), SOC005("apple");

        private String description;

        @JsonValue
        public String getDescription() {
            return description;
        }
    }
}


