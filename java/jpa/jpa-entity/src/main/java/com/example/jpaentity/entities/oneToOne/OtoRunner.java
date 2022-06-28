package com.example.jpaentity.entities.oneToOne;

import javax.persistence.EntityManager;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpaentity.entities.oneToOne.entities.OtoMember;
import com.example.jpaentity.entities.oneToOne.entities.OtoTeam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
//@Component
public class OtoRunner implements ApplicationRunner {

    private final EntityManager em;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("============= oto Runner Started =============");

        // update 문 나가지 않고 insert 문 2개
        /*OtoTeam team = OtoTeam.builder()
            .name("test-team")
            .build();
        em.persist(team);
        OtoMember member = OtoMember.builder()
            .name("test")
            .build();
        member.setTeam(team);
        em.persist(member);*/


        // insert 쿼리문 2개와 update 쿼리문이 나간다
        OtoMember member = OtoMember.builder()
            .name("test")
            .build();
        em.persist(member);

        OtoTeam team = OtoTeam.builder()
            .name("test-team")
            .build();
        em.persist(team);
        team.setMember(member);

        em.flush();
        em.clear();

        // LAZY로 지연 조회해도 member 쿼리문 나가고 team 조회문 추가로 나간다
        // 외래키가 대상 테이블에 있기 때문
        final OtoMember findMember = em.find(OtoMember.class, member.getMemberNo());
        final OtoTeam findTeam = findMember.getTeam();
        System.out.println(findTeam);
    }
}
