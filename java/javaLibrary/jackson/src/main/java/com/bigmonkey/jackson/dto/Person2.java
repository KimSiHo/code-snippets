package com.bigmonkey.jackson.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NON_PRIVATE)
@AllArgsConstructor
public class Person2 {

    Integer id;
    String name;
    String job;

    private Integer getIdTest() {
        return id;
    }

    @JsonIgnore
    public String getName2() {
        return name;
    }

    public String getJob() {
        return job;
    }
}
