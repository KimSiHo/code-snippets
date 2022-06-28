package com.example.jpaentity.entities.real.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
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
@Table(name = "t_member")
//@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_no")
    private Long memberNo;

    @Column
    private String name;

    @Column
    private LocalDateTime lastPasswordChangeDate;

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<SocialInfo> socialInfoList = new ArrayList<>();

    public static Member createMember() {
        return Member.builder()
                .name("test")
                .lastPasswordChangeDate(LocalDateTime.now())
                .build();
    }
}
