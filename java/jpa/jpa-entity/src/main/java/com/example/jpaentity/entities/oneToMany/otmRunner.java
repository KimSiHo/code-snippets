package com.example.jpaentity.entities.oneToMany;

import javax.persistence.EntityManager;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpaentity.entities.oneToMany.entities.OtmMember;
import com.example.jpaentity.entities.oneToMany.entities.OtmTeam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
//@Component
public class otmRunner implements ApplicationRunner {

    private final EntityManager em;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("============= otm Runner Started =============");

        OtmMember member = OtmMember.builder()
            .name("test")
            .build();
        em.persist(member);
        OtmMember member1 = OtmMember.builder()
            .name("test1")
            .build();
        em.persist(member1);
        log.info("insert is finish!");

        OtmTeam otmTeam = OtmTeam.builder()
            .name("team-name")
            .build();
        otmTeam.getMemberList().add(member);
        otmTeam.getMemberList().add(member1);
        log.info("member add finish!");

        em.persist(otmTeam);
        log.info("team persist is finish!");
    }
}
