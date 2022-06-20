package com.example.jpaentity.entities;

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
@Table(name = "social_info")
@Entity
public class SocialInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seq_social_info")
    private Long seq;

    @Column(name = "social_id", nullable = false, length = 100)
    private String socialId;

    @Column(name = "email", length = 100)
    private String email;

    //EAGER로 하면 left outer 조인으로 한번에 가져오고 LAZY로 설정하면 그냥 SocialInfo만 가져온다!
    @ManyToOne(fetch = FetchType.LAZY)
    //@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_no")
    private Member member;

    public static SocialInfo createSocialInfo() {
        return SocialInfo.builder()
            .socialId("socialId")
            .email("test@naver.com")
            .build();
    }

    @Override
    public String toString() {
        return "SocialInfo{" +
            "seq=" + seq +
            ", socialId='" + socialId + '\'' +
            ", email='" + email + '\'' +
            '}';
    }

    public void changeMember(Member member) {
        this.member = member;
        member.getSocialInfoList().add(this);
    }
}