package com.example.jpa.merge;

import javax.persistence.EntityManager;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MergeRunner implements ApplicationRunner {

    private final EntityManager em;

    // flush를 안쓰면 하이버네이트 내부 오류로 에러가 나는데, 영한님 피셜 detach한 후 다시 merge 하는 상황은 거의 없기에 크게 신경쓰지 말라고 한다
    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // merge는 비영속 엔티티도 저장하기에 persist와 같은 insert 쿼리문이 나간다!
        final Member member2 = Member.createMember();
        em.merge(member2);

        /*Member member = Member.createMember();
        em.persist(member);
        em.flush();
        em.detach(member);
        member.setPassword("test-password");

        // merge 메소드 호출하면 먼저 찾아야 되기 때문에 member를 sout으로 출력하기 전에 select 쿼리문이 먼저 나간다
        // sout 문이 나간 다음에 업데이트 쿼리문이 나간다!
        member = em.merge(member);
        System.out.println("member = " + member);*/
    }
}
