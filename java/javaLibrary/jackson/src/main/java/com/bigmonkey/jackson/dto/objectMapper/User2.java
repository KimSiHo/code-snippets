package com.bigmonkey.jackson.dto.objectMapper;

import java.util.List;

import com.bigmonkey.jackson.Config.Views;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class User2 {

    @JsonView(Views.NameOnly.class)
    private String name;

    @JsonView(Views.AgeAndName.class)
    private int age;
    private List<String> messages;



}
