package com.example.jpaentity.entities.oneToOne.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "oto_team")
//@Entity
public class OtoTeam {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "team_no")
    private Long no;

    @Column(name = "name")
    private String name;

    /*@OneToOne(mappedBy = "team")*/
    @OneToOne
    @JoinColumn(name = "member_no")
    private OtoMember member;
}
