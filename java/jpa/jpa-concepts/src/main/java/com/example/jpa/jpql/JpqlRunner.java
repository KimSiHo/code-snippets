package com.example.jpa.jpql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpa.identity.entities.IdentityMember;
import com.example.jpa.jpql.entities.JpqlMember;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
//@Component
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

        // 반환 타입이 명확할 때 사용
        TypedQuery<JpqlMember> query = em.createQuery("SELECT m FROM JpqlMember m where m.name=:name", JpqlMember.class);
        query.setParameter("name", "test");
        List<JpqlMember> resultList = query.getResultList();
        System.out.println("resultList = " + resultList.size());
        // 엔티티 프로젝션을 하면 대상이 되는 엔티티들이 전부다 영속성 컨텍스트에서 관리
        JpqlMember findMember = resultList.get(0);
        // 쿼리를 사용하면 flush 가 되니까 update 문이 중간에 나감 (밑에 쿼리 때문에)
        findMember.setName("update-name");

        // 반환 타입이 명확하지 않을 때 사용
        Query query1 = em.createQuery("SELECT m.name, m.age FROM JpqlMember m");
        List resultList1 = query1.getResultList();
        System.out.println("resultList1 = " + resultList1.size());
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
