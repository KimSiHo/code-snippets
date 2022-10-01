package com.example.jpaentity.entities.value.entities;

import java.util.Objects;

import javax.persistence.Column;
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
    @Column(length = 5)
    private String zipcode;

    // 엔티티에 대한 참조도 가질 수 있다!
    /*private ValueMember member;*/

    // 이렇게 연관 있는 메소드를 만들고 @Column 정의를 해서 응집도를 높일 수 있다
    public boolean isValid() {
        return true;
    }

    public void changeCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return Objects.equals(getCity(), address.getCity()) && Objects.equals(getStreet(), address.getStreet()) && Objects.equals(getZipcode(), address.getZipcode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getStreet(), getZipcode());
    }
}
