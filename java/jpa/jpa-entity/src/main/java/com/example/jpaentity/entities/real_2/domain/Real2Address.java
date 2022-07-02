package com.example.jpaentity.entities.real_2.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Real2Address {

    private String city;
    private String street;
    private String zipcode;

    protected Real2Address() {
    }

    public Real2Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
