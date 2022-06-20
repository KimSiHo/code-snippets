package com.example.jpaentity.entities;

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

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "service_terms_agree")
@Entity
public class ServiceTermsAgree {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seq_service_terms_agree")
    private Long seqServiceTermsAgree;

    @Column(name = "member_no")
    private Long memberNo;

    @Column(name = "is_agree", nullable = false)
    private Boolean agree;
}
