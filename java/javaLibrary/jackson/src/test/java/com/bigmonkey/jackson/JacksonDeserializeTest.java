package com.bigmonkey.jackson;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.ToString;

@SpringBootTest
public class JacksonDeserializeTest {

    @DisplayName("기본 readValue 메소드 테스트")
    @Test
    public void basicReadTest() throws JsonProcessingException {
        String jsonStr = "{\"id\" : 1, \"name\" : \"Anna\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        Student student = objectMapper.readValue(jsonStr, Student.class);

        System.out.println(student);
    }

    @DisplayName("type reference를 사용한 변환 테스트")
    @Test
    public void typeReferenceTest() throws JsonProcessingException {
        String jsonStr = "{\"id\" : 1, \"name\" : \"Anna\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(jsonStr, new TypeReference<>() {
        });

        System.out.println(jsonMap);
    }

    @DisplayName("json array를 자바 배열로 변환 테스트")
    @Test
    public void jsonArrayTest() throws JsonProcessingException {
        String jsonArrStr = "[{\"id\" : 1, \"name\" : \"Anna\"}, {\"id\" : 2, \"name\" : \"Brian\"}]";
        ObjectMapper objectMapper = new ObjectMapper();
        Student[] studentArr = objectMapper.readValue(jsonArrStr, Student[].class);

        System.out.println(Arrays.toString(studentArr));
    }

    @DisplayName("json array를 자바 리스트로 변환 테스트")
    @Test
    public void jsonListTest() throws JsonProcessingException {
        String jsonArrStr = "[{\"id\" : 1, \"name\" : \"Anna\"}, {\"id\" : 2, \"name\" : \"Brian\"}]";
        ObjectMapper objectMapper = new ObjectMapper();
        List<Student> studentList = objectMapper.readValue(jsonArrStr, new TypeReference<>() {});

        System.out.println(studentList);
    }

    @AllArgsConstructor
    @ToString
    public static class Student {

        private int id;
        private String name;

        public Student() {
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
