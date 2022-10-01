package com.example.jpaentity.entities.real_2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

import com.example.jpaentity.entities.real_2.domain.item.Real2Item;

@Entity
@Getter @Setter
public class Real2Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Real2Item> items = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Real2Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Real2Category> child = new ArrayList<>();

    //==연관관계 메서드==//
    public void addChildCategory(Real2Category child) {
        this.child.add(child);
        child.setParent(this);
    }

}
