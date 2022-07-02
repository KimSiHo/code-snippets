package com.example.jpaentity.entities.real_2.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("B")
@Getter
@Setter
public class Real2Book extends Real2Item {

    private String author;
    private String isbn;
}
