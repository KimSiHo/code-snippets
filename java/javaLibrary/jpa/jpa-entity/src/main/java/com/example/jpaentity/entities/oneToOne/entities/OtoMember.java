package com.example.jpaentity.entities.oneToOne.entities;

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
@Table(name = "oto_member")
//@Entity
public class OtoMember {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_no")
    private Long memberNo;

    @Column(name = "name")
    private String name;

    /*// 멤버가 일대일 연관 관계의 주인
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_no")
    private OtoTeam team;*/

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private OtoTeam team;
}
