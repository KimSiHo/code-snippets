package com.example.jpaentity.entities.manyToOne.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "mto_member")
//@Entity
public class MtoMember {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_no")
    private Long memberNo;

    @Column(name = "name")
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<MtoSocialInfo> socialInfoList = new ArrayList<>();

    public static MtoMember createMember() {
        return MtoMember.builder()
            .name("test")
            .build();
    }
}
