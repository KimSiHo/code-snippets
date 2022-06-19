package com.bigmonkey.jackson.dto.objectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
/*@AllArgsConstructor*/
public class Car {

    private String color;
    private String type;

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }
}
