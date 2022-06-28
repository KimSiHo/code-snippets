package com.example.jpaentity.entities.manyToOne;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpaentity.entities.manyToOne.entities.MtoMember;
import com.example.jpaentity.entities.manyToOne.entities.MtoSocialInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
//@Component
public class MtoRunner implements ApplicationRunner {

    private final EntityManager em;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("============= mto Runner Started =============");

        MtoMember member = MtoMember.createMember();
        em.persist(member);

        MtoSocialInfo socialInfo = MtoSocialInfo.createSocialInfo();
        socialInfo.changeMember(member);
        em.persist(socialInfo);

        MtoSocialInfo socialInfo1 = MtoSocialInfo.createSocialInfo();
        socialInfo1.changeMember(member);
        em.persist(socialInfo1);

        MtoSocialInfo socialInfo2 = MtoSocialInfo.createSocialInfo();
        socialInfo2.changeMember(member);
        em.persist(socialInfo2);
        log.info("save finished!");

        em.flush();
        em.clear();

        // left out 조인 되는 것을 inner 조인으로 바꾸자!
        MtoSocialInfo findSocialInfo = em.find(MtoSocialInfo.class, socialInfo.getSeq());
        List<MtoSocialInfo> socialInfoList = findSocialInfo.getMember().getSocialInfoList();

        for (MtoSocialInfo info : socialInfoList) {
            info.setSocialId("afterChange");
        }
        log.info("logic end!");
    }
}
