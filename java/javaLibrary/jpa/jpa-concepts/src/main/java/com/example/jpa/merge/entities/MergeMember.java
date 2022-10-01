package com.example.jpa.merge.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
//@Entity
@Table(name = "merge_member")
public class MergeMember {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_no")
    private Long memberNo;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(columnDefinition = "CHAR", nullable = false, length = 6)
    private String joinCountryCode;

    @Column
    private LocalDateTime lastPasswordChangeDate;

    public static MergeMember createMember() {
        return MergeMember.builder()
                .password("password")
                .joinCountryCode("a")
                .lastPasswordChangeDate(LocalDateTime.now())
                .build();
    }
}
