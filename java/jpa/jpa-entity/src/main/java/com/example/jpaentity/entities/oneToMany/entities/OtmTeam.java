package com.example.jpaentity.entities.oneToMany.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
@Table(name = "otm_team")
//@Entity
public class OtmTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "team_no")
    private Long no;

    @Column(name = "name")
    private String name;

    @Builder.Default
    @OneToMany
    @JoinColumn(name = "team_no")
    private List<OtmMember> memberList = new ArrayList<>();
}
