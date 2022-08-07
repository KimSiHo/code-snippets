package com.bigmonkey.jackson;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;

@SpringBootTest
public class JacksonSerializeTest {

    @DisplayName("기본 write 메소드 테스트")
    @Test
    public void basicWriteTest() throws JsonProcessingException {
        Student student = new Student(1, "Anna");
        ObjectMapper objectMapper = new ObjectMapper();
        String studentJson = objectMapper.writeValueAsString(student);

        System.out.println(studentJson);
    }

    @DisplayName("map write 메소드 테스트")
    @Test
    public void basicMapTest() throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("name", "Anna");
        ObjectMapper objectMapper = new ObjectMapper();
        String studentJson = objectMapper.writeValueAsString(map);

        System.out.println(studentJson);
    }

    @AllArgsConstructor
    @Data
    public static class Student {

        private int id;
        private String name;
    }
}
