package me.bigmonkey.mvc.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

//setter 필요 setter 없을 시 에러
@ToString
@Setter
@Getter
@NoArgsConstructor
public class RpDto {

    String id;
    String name;
}
