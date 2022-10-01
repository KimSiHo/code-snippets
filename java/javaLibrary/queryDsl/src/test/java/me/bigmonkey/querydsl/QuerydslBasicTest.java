package me.bigmonkey.querydsl;

import static me.bigmonkey.querydsl.entities.QMember.member;
import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;

import me.bigmonkey.querydsl.entities.Member;
import me.bigmonkey.querydsl.entities.QMember;
import me.bigmonkey.querydsl.entities.Team;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

    @PersistenceContext
    EntityManager em;

    // 동시성 문제 없어 필드 타입으로 빼서 사용하는 것을 추천
    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);
        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
    }

    @Test
    public void startJPQL() {
        //member1을 찾아라.
        String qlString = "select m from Member m where m.username = :username";
        Member findMember = em.createQuery(qlString, Member.class)
            .setParameter("username", "member1")
            .getSingleResult();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    // 컴파일 시점에 에러를 잡아준다
    // Querydsl은 JPQL 빌더이기 때문에 결국에는 JPQL이 나간다, Querydsl이 만드는 JPQL을 보고 싶으면 use_sql_comment 옵션을 true로 주면 실행하는 JPQL을 볼 수 있다
    @Test
    public void startQuerydsl() {
        //member1을 찾아라.
        Member findMember = queryFactory
            .select(member)
            .from(member)
            .where(member.username.eq("member1")) //파라미터 바인딩 자동 처리
            .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }
}
