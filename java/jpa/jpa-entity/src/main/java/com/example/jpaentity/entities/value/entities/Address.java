package com.example.jpaentity.entities.value.entities;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {
    private String city;
    private String street;
    private String zipcode;

    // 엔티티에 대한 참조도 가질 수 있다!
    /*private ValueMember member;*/

    public void changeCity(String city) {
        this.city = city;
    }
}
