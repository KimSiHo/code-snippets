package com.bigmonkey.jackson;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

public class JacksonAnnotationTest {

    @DisplayName("")
    @Test
    public void test1() {
        ObjectMapper objectMapper = new ObjectMapper();

    }




    @Getter
    @Setter
    public static class Student {
        int age;
        String name;

        public Student() {
        }

        public Student(int age, String name) {
            this.age = age;
            this.name = name;
        }


    }
}
