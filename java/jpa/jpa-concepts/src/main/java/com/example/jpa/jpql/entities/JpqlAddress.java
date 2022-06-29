package com.example.jpa.jpql.entities;

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
public class JpqlAddress {
    private String city;
    private String street;
    @Column
    private String zipcode;

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
        JpqlAddress jpqlAddress = (JpqlAddress) o;
        return Objects.equals(getCity(), jpqlAddress.getCity()) && Objects.equals(getStreet(), jpqlAddress.getStreet()) && Objects.equals(getZipcode(), jpqlAddress.getZipcode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getStreet(), getZipcode());
    }
}