package com.bigmonkey.jackson.dto.objectMapper;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Car2 {

    private String color;
    private String type;
    private Integer money;
}
