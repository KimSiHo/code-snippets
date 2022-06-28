package com.example.jpaentity.entities.manyToOne;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpaentity.entities.manyToOne.entities.MtoMember;
import com.example.jpaentity.entities.manyToOne.entities.MtoSocialInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
//@Component
public class NPlus1Runner implements ApplicationRunner {

    private final EntityManager em;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("============= N+1 Runner Started =============");
        this.init();
        em.flush();
        em.clear();

        /*List<MtoSocialInfo> list = em.createQuery("select msi from MtoSocialInfo msi", MtoSocialInfo.class).getResultList();
        System.out.println(list.size());*/

        // inner 조인으로 한 번에 가져옴
        // 이렇게 함으로써 기본으로 필요 없을 시 가져오지 않게 (LAZY) 설정하고 필요 시 fetch 조인으로 한 번에 가져오면 된다
        List<MtoSocialInfo> list = em.createQuery("select msi from MtoSocialInfo msi join fetch msi.member", MtoSocialInfo.class).getResultList();
        System.out.println(list.size());
    }

    public void init() {
        MtoMember member = MtoMember.createMember();
        MtoMember member1 = MtoMember.createMember();
        MtoMember member2 = MtoMember.createMember();
        MtoMember member3 = MtoMember.createMember();
        MtoMember member4 = MtoMember.createMember();
        em.persist(member);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        MtoSocialInfo socialInfo = MtoSocialInfo.createSocialInfo();
        MtoSocialInfo socialInfo1 = MtoSocialInfo.createSocialInfo();
        MtoSocialInfo socialInfo2 = MtoSocialInfo.createSocialInfo();
        MtoSocialInfo socialInfo3 = MtoSocialInfo.createSocialInfo();
        MtoSocialInfo socialInfo4 = MtoSocialInfo.createSocialInfo();
        socialInfo.changeMember(member);
        socialInfo1.changeMember(member1);
        socialInfo2.changeMember(member2);
        socialInfo3.changeMember(member3);
        socialInfo4.changeMember(member4);

        em.persist(socialInfo);
        em.persist(socialInfo1);
        em.persist(socialInfo2);
        em.persist(socialInfo3);
        em.persist(socialInfo4);
    }
}
