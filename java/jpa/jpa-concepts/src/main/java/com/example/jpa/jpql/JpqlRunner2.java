package com.example.jpa.jpql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.JMenu;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpa.jpql.entities.JpqlMember;
import com.example.jpa.jpql.entities.JpqlTeam;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JpqlRunner2 implements ApplicationRunner {

    private final EntityManager em;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.init();
        System.out.println("============= Jpql Runner2 Started =============");

        // 둘 다 같은 join 쿼리가 나가지만, join 은 튜닝할 요소가 많고 성능을 잡아먹는 쿼리문이므로 아래처럼 join을 쓴다는 것을 명시하는 것이 좋다
        /*TypedQuery<JpqlTeam> query = em.createQuery("select m.team from JpqlMember m", JpqlTeam.class);
        List<JpqlTeam> resultList = query.getResultList();
        System.out.println("resultList = " + resultList.size());

        TypedQuery<JpqlTeam> query1 = em.createQuery("select t from JpqlMember m join m.team t", JpqlTeam.class);
        List<JpqlTeam> resultList1 = query1.getResultList();
        System.out.println("resultList1 = " + resultList1.size());*/

        // 여러 값을 조회하는 경우
        /*Query query = em.createQuery("select m.name, m.age from JpqlMember m");
        List<Object[]> resultList = query.getResultList();
        Object[] result = resultList.get(0);
        System.out.println("name = " + result[0]);
        System.out.println("age = " + result[1]);*/

        // 이 방법이 여러 값을 조회할 때 가장 깔끔, QueryDSL을 사용하면 임포트해서 클래스명만 적는 게 가능
        /*List<MemberDto> resultList =
            em.createQuery("select new com.example.jpa.jpql.MemberDto(m.name, m.age) from JpqlMember m").getResultList();

        MemberDto memberDto = resultList.get(0);
        System.out.println("memberDto = " + memberDto);*/

        // 경로 표현식, 묵시적 조인은 cross join 이 나가므로 명시적 조인을 사용하자
        //String query = "select t.name from JpqlMember m join m.team t";
        /*String query = "select m.team.name from JpqlMember m";
        TypedQuery<String> findQuery = em.createQuery(query, String.class);
        List<String> resultList = findQuery.getResultList();
        System.out.println("resultList = " + resultList.size());*/

        // 컬렉션 타입이므로 어떤 필드를 출력할지 애매모해서 더 이상 탐색이 안된다
        /*String query = "select t.members from JpqlTeam t";
        Query query1 = em.createQuery(query);
        List resultList = query1.getResultList();
        System.out.println("resultList = " + resultList.size());
        for (Object o : resultList) {
            System.out.println("o = " + o);
        }*/

        // size 정도만 탐색이 된다
        /*String query = "select t.members.size from JpqlTeam t";
        Query query1 = em.createQuery(query);
        List resultList = query1.getResultList();
        System.out.println("resultList = " + resultList.size());
        for (Object o : resultList) {
            System.out.println("o = " + o);
        }*/

        // fetch 조인하면 한 번에 다 가져온다, 바로 아래 쿼리도 다 가져오기는 하지만 fetch join을 쓰면 객체에 셋팅까지 다 해주는 게 장점인 것 같다!
        /*String query = "select m, t from JpqlMember m join m.team t";
        Query query1 = em.createQuery(query);
        List resultList = query1.getResultList();
        System.out.println("resultList = " + resultList.size());*/

        /*String query = "select m from JpqlMember m join fetch m.team";
        TypedQuery<JpqlMember> query1 = em.createQuery(query, JpqlMember.class);
        List<JpqlMember> resultList = query1.getResultList();
        for (JpqlMember jpqlMember : resultList) {
            System.out.println(jpqlMember.getName() + ", " + jpqlMember.getTeam().getName());
        }*/

        // 컬렉션을 페치 조인하면 데이터가 뻥튀기 된다
        /*String query = "select t from JpqlTeam t join fetch t.members";
        List<JpqlTeam> resultList = em.createQuery(query, JpqlTeam.class).getResultList();

        for (JpqlTeam jpqlTeam : resultList) {
            System.out.println("team = " + jpqlTeam.getName() + "|members=" + jpqlTeam.getMembers().size());
            for (JpqlMember member:jpqlTeam.getMembers()) {
                System.out.println("member ->" + member);
            }
        }*/

        // 페치 조인과 distinct > SQL에 distinct를 추가하지만 데이터가 다르므로 SQL 결과에서 중복제거 실패
        // distinct가 추가로 애플리케이션에서 중복 제거를 한다
        /*String query = "select distinct t from JpqlTeam t join fetch t.members";
        List<JpqlTeam> resultList = em.createQuery(query, JpqlTeam.class).getResultList();

        for (JpqlTeam jpqlTeam : resultList) {
            System.out.println("team = " + jpqlTeam.getName() + "|members=" + jpqlTeam.getMembers().size());
            for (JpqlMember member:jpqlTeam.getMembers()) {
                System.out.println("member ->" + member);
            }
        }*/

        // 페치 조인의 한계 > 둘 이상의 컬렉션은 페치 조인 할 수 없다
        // 컬렉션을 페치 조인하면 페이징 API를 사용 X (대일 같은 단일 값 연관 필드들은 페치 조인해도 페이징 가능)
        // 하이버네이트는 경고 로그를 남기고 메모리에서 페이징 (매우 위험)
        // 이것은 매우 위험한 코드이다! jpa는 객체 그래프를 통해서 컬렉션을 가져올 때 해당 컬렉션을 필터링 해서 가져오는 것이 아니라 전체
        // 다 가져오는 것으로 설계되어 있다
        //String query = "select t from JpqlTeam t join fetch t.members m where m.age > 10";

        // 페이징 메소드를 호출했는데 페이지 쿼리가 안 나간다, 데이터를 메모리에 다 올려서 처리하는 것이다!
        // 일대 다 페치 조인하면 데이터가 뻥튀기 되고, 그로 인해서 의도된 대로 데이터를 받을 수 없으므로 경고 로그를 남기고 메모리에서 페이징 처리를 한다
        // 해당 방법은 쓰면 안된다, 일대 다 컬렉션 페치 조인 페이징 처리를 푸는 방법은 몇 가지가 있다
        // 하나는 일대 다 조회를 가능할 경우 다대 일로 바꿔서 조회하는 것이다, 그러면 데이터 뻥튀기가 안되므로 정상적으로 페이징 API 사용 가능
        // 그리고 다른 방법은 페치 조인을 괌감히 없앤다!
        /*String query = "select t from JpqlTeam t join fetch t.members";
        List<JpqlTeam> resultList = em.createQuery(query, JpqlTeam.class)
            .setFirstResult(0)
            .setMaxResults(1)
            .getResultList();

        System.out.println("resultList = " + resultList.size());*/
    }

    private void init() {
        final JpqlTeam team = JpqlTeam.create("team");
        final JpqlTeam team1 = JpqlTeam.create("team1");
        final JpqlTeam team2 = JpqlTeam.create("team2");
        em.persist(team);
        em.persist(team1);
        em.persist(team2);

        final JpqlMember member = JpqlMember.createMember("test");
        final JpqlMember member1 = JpqlMember.createMember("test1");
        final JpqlMember member2 = JpqlMember.createMember("test2");
        final JpqlMember member3 = JpqlMember.createMember("test3");
        member.setTeam(team);
        member1.setTeam(team1);
        member2.setTeam(team2);
        member3.setTeam(team);

        em.persist(member);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);

        em.flush();
        em.clear();
    }
}
