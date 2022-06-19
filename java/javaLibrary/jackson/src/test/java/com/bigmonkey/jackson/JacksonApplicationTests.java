package com.bigmonkey.jackson;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

@SpringBootTest
class JacksonApplicationTests {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void contextLoads() {
    }
}
