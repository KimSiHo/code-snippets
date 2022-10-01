package com.example.jpaentity.entities.mapping;

import javax.persistence.EntityManager;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpaentity.entities.mapping.mappedSuperClass.MscMember;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
//@Component
public class MappedSuperClassRunner implements ApplicationRunner {

    private final EntityManager em;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("============= mapped super class Runner Started =============");

        final MscMember member = MscMember.createMember();
        em.persist(member);
    }
}
