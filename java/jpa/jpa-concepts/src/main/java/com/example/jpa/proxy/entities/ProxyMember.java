package com.example.jpa.proxy.entities;

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
@Entity
@Table(name = "proxy_member")
public class ProxyMember {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_no")
    private Long memberNo;

    @Column
    private String password;

    @Column
    private LocalDateTime lastPasswordChangeDate;

    public static ProxyMember createMember() {
        return ProxyMember.builder()
                .password("password")
                .lastPasswordChangeDate(LocalDateTime.now())
                .build();
    }
}
