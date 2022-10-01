package com.example.jpaentity.entities.join.entities;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Entity
//@Inheritance
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public class Item {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;

}
