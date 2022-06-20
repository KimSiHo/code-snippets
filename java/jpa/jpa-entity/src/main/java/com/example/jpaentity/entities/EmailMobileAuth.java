package com.example.jpaentity.entities;

import java.time.LocalDateTime;
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
@Table(name = "email_mobile_auth")
@Entity
public class EmailMobileAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seq_email_mobile_auth")
    private Long seq;

    @Column(name = "seq_member_person")
    private Long seqMemberPerson;

    @Column(name = "seq_social_info")
    private Long seqSocialInfo;

    @Column(name = "auth_number")
    private String authNumber;

    @Column(name = "member_no")
    private Long memberNo;

    @Column(name = "auth_success_date")
    private LocalDateTime successDate;

    @Builder.Default
    @OneToMany
    @JoinColumn(name = "seq_email_mobile_auth")
    private final List<MemberAccount> memberAccounts = new ArrayList<>();
}
