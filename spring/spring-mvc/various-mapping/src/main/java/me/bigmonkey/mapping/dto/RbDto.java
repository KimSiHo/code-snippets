package me.bigmonkey.mapping.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

//setter가 없어도 되고 기본 생성자 필요, getter 활용 getter 없을 시 에러
@ToString
@NoArgsConstructor
public class RbDto {

    String id;
    String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
