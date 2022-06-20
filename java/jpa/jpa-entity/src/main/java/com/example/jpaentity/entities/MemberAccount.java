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

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_account")
@Entity
public class MemberAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seq_member_account")
    private Long seq;

    @Column(name = "country_number", nullable = false, length = 20)
    private String countryNumber;

    @Column(name = "id", nullable = false, length = 150)
    private String id;

    @Column(name = "seq_email_mobile_auth")
    private Long seqEmailMobileAuth;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_no")
    private Member member;
}