package com.example.jpaentity.entities.cascade.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "c_child")
//@Entity
public class Child {

    @Id @GeneratedValue
    private Long id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_no")
    private Parent parent;

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public static Child createChild() {
        return Child.builder()
            .name("child")
            .build();
    }
}
