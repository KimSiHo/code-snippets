package com.example.jpa.key_identity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
//@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "t_refreshtoken_info")
public class TRefreshTokenInfo extends BaseRegisterDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq_refreshtoken_info")
    private Long seqRefreshTokenInfo;

    @Column(name = "seq_client_info", nullable = false)
    private Long seqClientInfo;

    @Column(name = "seq_authcode_info", nullable = false)
    private Long seqAuthCodeInfo;

    @Column(name = "member_no", nullable = false)
    private Long memberNo;

    @Column(name = "service_member_no", nullable = false)
    private Long serviceMemberNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_code", nullable = false)
    private ServiceCode serviceCode;

    @Column(name = "client_id", nullable = false)
    private String clientId;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;
}
