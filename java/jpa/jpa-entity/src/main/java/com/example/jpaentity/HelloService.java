package com.example.jpaentity;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpaentity.entities.Member;
import com.example.jpaentity.entities.SocialInfo;
import com.example.jpaentity.entities.oneToMany.Member2;
import com.example.jpaentity.entities.oneToMany.Team;
import com.example.jpaentity.entities.oneToOne.Member3;
import com.example.jpaentity.entities.oneToOne.Team2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class HelloService {

    private final EntityManager em;

    @Transactional
    public void doTest01() {
        Member member = Member.createMember();
        em.persist(member);

        SocialInfo socialInfo = SocialInfo.createSocialInfo();
        socialInfo.changeMember(member);
        em.persist(socialInfo);

        SocialInfo socialInfo2 = SocialInfo.createSocialInfo();
        socialInfo2.changeMember(member);
        em.persist(socialInfo2);

        SocialInfo socialInfo3 = SocialInfo.createSocialInfo();
        socialInfo3.changeMember(member);
        em.persist(socialInfo3);

        // 연관관계 편의 메서드로 대체
  /*      member.getSocialInfoList().add(socialInfo);
        member.getSocialInfoList().add(socialInfo2);
        member.getSocialInfoList().add(socialInfo3);*/

        em.flush();
        em.clear();

        // left out 조인 되는 것을 inner 조인으로 바꾸자!
        SocialInfo findSocialInfo = em.find(SocialInfo.class, socialInfo.getSeq());
        List<SocialInfo> socialInfoList = findSocialInfo.getMember().getSocialInfoList();


        for (SocialInfo info : socialInfoList) {
            info.setEmail("afterChange@naver.com");
            log.info(info.toString());
        }

    }

    @Transactional
    public void doTest02() {
        Member2 member1 = Member2.builder()
            .name("test1")
            .build();

        em.persist(member1);

        Member2 member2 = Member2.builder()
            .name("test2")
            .build();

        em.persist(member2);

        Team team = Team.builder()
            .name("team-name")
            .build();

        team.getMember2List().add(member1);
        team.getMember2List().add(member2);

        em.persist(team);
    }

    @Transactional
    public void doTest03() {

       /* Team2 team = Team2.builder()
            .name("test-team")
            .build();

        em.persist(team);

        Member3 member = Member3.builder()
            .name("test")
            .build();

        member.setTeam2(team);

        em.persist(member);*/

        Team2 team = Team2.builder()
            .name("test-team")
            .build();
        //team.setMember3(member);
        em.persist(team);

        Member3 member = Member3.builder()
            .name("test")
            .build();
        em.persist(member);
        //member.setTeam2(team);
        team.setMember3(member);

        em.flush();
        em.clear();

        final Member3 member3 = em.find(Member3.class, member.getMemberNo());
        final Team2 team2 = member3.getTeam2();
        System.out.println(member3);
    }
}
