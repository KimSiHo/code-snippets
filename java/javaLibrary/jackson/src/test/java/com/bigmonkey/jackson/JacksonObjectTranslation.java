package com.bigmonkey.jackson;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SpringBootTest
public class JacksonObjectTranslation {

    @DisplayName("object -> json node 변환 테스트")
    @Test
    public void objectToJsonNode() throws JsonProcessingException {
        Student student = new Student(1, "Anna");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode1 = objectMapper.valueToTree(student);
        JsonNode jsonNode2 = objectMapper.convertValue(student, JsonNode.class);

        System.out.println(jsonNode1);
        System.out.println(jsonNode2);
    }

    @DisplayName("json 문자열 -> json node 변환 테스트")
    @Test
    public void jsonStrToJsonNode() throws JsonProcessingException {
        String jsonStr = "{\"id\":1,\"name\":\"Anna\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode1 = objectMapper.readTree(jsonStr);
        JsonNode jsonNode2 = objectMapper.readValue(jsonStr, JsonNode.class);

        System.out.println(jsonNode1);
        System.out.println(jsonNode2);
    }

    @DisplayName("json str -> object 변환 테스트")
    @Test
    public void jsonStrToObject() throws JsonProcessingException {
        String jsonStr = "{\"id\" : 1, \"name\" : \"Anna\"}";
        ObjectMapper objectMapper = new ObjectMapper();

        Student student = objectMapper.readValue(jsonStr, Student.class);

        System.out.println(student);
    }

    /**
     * readTree() 메소드는 Json 문자열을 받아서 JsonNode 객체를 리턴합니다.
     *
     * readValue() 메소드는 2번째 파라미터로, Json문자열을 변환할 클래스 타입을 입력받습니다.
     * 그리고, 이 2번째 파라미터로 입력받은 클래스 타입의 객체를 리턴합니다.
     * 따라서, 이 메소드를 사용하면, Json 문자열을 JsonNode 뿐만 아니라 다른 객체 타입으로도 변환할 수 있습니다.
     */

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Student {

        private int id;
        private String name;
    }
}
