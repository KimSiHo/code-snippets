package com.example.jpaentity.entities.mapping.entities;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.example.jpaentity.entities.mapping.RoleType;

@Table(name = "simple_mapping", uniqueConstraints = {@UniqueConstraint(name = "NAME_AGE_UNIQUE", columnNames = {"NAME", "AGE"} )})
//@Entity
public class SimpleMapping {

    @Id
    @GeneratedValue
    private Long id;

    // 이렇게 단일로 주는 unique는 잘 쓰지 않는다, 단일 컬럼 뿐이 안되고 이름이 랜덤 생성된 문자열이어서 파악하기 힘들다
    @Column(unique = true, length = 10)
    private String name;

    @Column
    private int age;

    @Enumerated(value = EnumType.STRING)
    private RoleType roleType;
}
