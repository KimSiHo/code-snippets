package com.example.jpaentity.entities.oneToOne;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "team2")
@Entity
public class Team2 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "team_no")
    private Long no;

    @Column(nullable = false, length = 100)
    private String name;

    /*@OneToOne(mappedBy = "team2")*/
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member3 member3;

/*    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_fk")
    private Member3 member3;*/

}
