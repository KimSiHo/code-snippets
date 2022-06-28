package com.example.jpaentity.entities.mapping.mappedSuperClass;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.jpaentity.entities.real.entities.SocialInfo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "msc_member")
//@Entity
public class MscMember extends BaseRegisterDateTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_no")
    private Long memberNo;

    @Column
    private String name;

    @Column
    private LocalDateTime lastPasswordChangeDate;

    public static MscMember createMember() {
        return MscMember.builder()
                .name("test")
                .lastPasswordChangeDate(LocalDateTime.now())
                .build();
    }
}
