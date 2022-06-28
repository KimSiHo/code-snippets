package com.example.jpa.proxy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;

import org.hibernate.Hibernate;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpa.merge.entities.MergeMember;
import com.example.jpa.proxy.entities.ProxyMember;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class ProxyRunner implements ApplicationRunner {

    private final EntityManager em;
    private final EntityManagerFactory emf;

    // 디버깅으로 시도하면 쿼리가 바로 나감. 그냥 한번에 실행하는 것으로 해야지 제대로 된 결과를 볼 수 있다
    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("============= Proxy Runner Started =============");
        final ProxyMember member = ProxyMember.createMember();
        em.persist(member);
        em.flush();
        em.clear();

        ProxyMember reference = em.getReference(ProxyMember.class, 1L);
        // 초기화 코드를 주석해제하면 해당 시점에 조회 쿼리 나감
        /*System.out.println("persistenceUnitUtil.isLoaded(reference) :" + emf.getPersistenceUnitUtil().isLoaded(reference));
        Hibernate.initialize(reference);
        System.out.println("persistenceUnitUtil.isLoaded(reference) :" + emf.getPersistenceUnitUtil().isLoaded(reference));*/

        log.info("proxy class : {}", reference.getClass());
        log.info("query will go out");
        log.info(reference.getPassword());
    }
}
