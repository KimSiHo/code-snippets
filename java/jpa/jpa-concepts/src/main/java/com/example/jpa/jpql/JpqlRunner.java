package com.example.jpa.jpql;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpa.identity.entities.IdentityMember;
import com.example.jpa.jpql.entities.JpqlMember;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JpqlRunner implements ApplicationRunner {

    private final EntityManager em;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("============= Jpql Runner Started =============");
        this.init();

        String jpql = "select jm from JpqlMember jm where jm.name like '%test%'";
        final List<JpqlMember> list = em.createQuery(jpql, JpqlMember.class).getResultList();
        System.out.println("list.size() :" + list.size());


    }

    private void init() {
        final JpqlMember member = JpqlMember.createMember("test");
        final JpqlMember member1 = JpqlMember.createMember("test1");
        final JpqlMember member2 = JpqlMember.createMember("test2");

        em.persist(member);
        em.persist(member1);
        em.persist(member2);

        em.flush();
        em.clear();
    }
}
