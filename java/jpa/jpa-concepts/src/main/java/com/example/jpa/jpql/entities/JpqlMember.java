package com.example.jpa.jpql.entities;

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
@Table(name = "jpql_member")
public class JpqlMember {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_no")
    private Long memberNo;

    @Column
    private String name;

    @Column
    private LocalDateTime lastPasswordChangeDate;

    public static JpqlMember createMember(String name) {
        return JpqlMember.builder()
                .name(name)
                .lastPasswordChangeDate(LocalDateTime.now())
                .build();
    }
}
