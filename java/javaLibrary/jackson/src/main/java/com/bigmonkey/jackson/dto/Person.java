package com.bigmonkey.jackson.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Person {

    Integer id;
    String name;

    @JsonProperty("jobTest")
    String job;

    public Integer getIdName() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }
}
