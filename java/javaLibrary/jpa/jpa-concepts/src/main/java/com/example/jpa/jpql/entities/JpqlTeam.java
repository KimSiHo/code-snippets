package com.example.jpa.jpql.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

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
@Table(name = "jpql_team")
public class JpqlTeam {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "team_id")
    private Long teamId;

    @Column
    private String name;

    @BatchSize(size = 100)
    @Builder.Default
    @OneToMany(mappedBy = "team")
    private List<JpqlMember> members = new ArrayList<>();

    public static JpqlTeam create(String name) {
        return JpqlTeam.builder()
            .name(name)
            .build();
    }
}
