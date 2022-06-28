package com.example.jpa.proxy;

import javax.persistence.EntityManager;

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
        log.info("proxy class : {}", reference.getClass());
        log.info("query will go out");
        log.info(reference.getPassword());
    }
}
