package com.example.jpa.jpql;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private String name;
    private int age;

    @Override
    public String toString() {
        return "MemberDto{" +
            "name='" + name + '\'' +
            ", age=" + age +
            '}';
    }
}
