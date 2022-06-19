package com.bigmonkey.jackson.dto.objectMapper;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class User4 {

    private String name;
    private int age;
    private List<String> messages;
}
