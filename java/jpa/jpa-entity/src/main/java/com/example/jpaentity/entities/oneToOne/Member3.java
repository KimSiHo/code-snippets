package com.example.jpaentity.entities.oneToOne;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member3")
@Entity
public class Member3 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_no")
    private Long memberNo;

    @Column(nullable = false, length = 100)
    private String name;

    // 멤버가 일대일 연관 관계의 주인
    /*@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_no")*/

    @OneToOne(mappedBy = "member3", fetch = FetchType.LAZY)
    private Team2 team2;

    /*@OneToOne(mappedBy = "member3", fetch = FetchType.LAZY)
    private Team2 team2;*/

}
