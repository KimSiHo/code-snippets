package com.example.jpaentity.entities.manyToOne.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "mto_social_info")
//@Entity
public class MtoSocialInfo {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seq_social_info")
    private Long seq;

    @Column(name = "social_id")
    private String socialId;

    //EAGER로 하면 left outer 조인으로 한번에 가져오고 LAZY로 설정하면 그냥 SocialInfo만 가져온다!
    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_no")
    private MtoMember member;

    public static MtoSocialInfo createSocialInfo() {
        return MtoSocialInfo.builder()
            .socialId("socialId")
            .build();
    }

    public void changeMember(MtoMember mtoMember) {
        this.member = mtoMember;
        mtoMember.getSocialInfoList().add(this);
    }
}