package com.example.jpaentity.entities.oneToMany;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member2")
@Entity
public class Member2 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_no")
    private Long memberNo;

    @Column(nullable = false, length = 100)
    private String name;

    // 일대 다 양방향은 공식적으로 존재하지 않고 읽기 전용 필드를 사용해서 양방향 처럼 사용하는 것이다
    /*@ManyToOne
    @JoinColumn(name = "team_no", insertable = false, updatable = false)
    private Team team;*/

}
