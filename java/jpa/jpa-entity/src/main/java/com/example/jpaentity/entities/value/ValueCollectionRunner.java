package com.example.jpaentity.entities.value;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpaentity.entities.value.entities.Address;
import com.example.jpaentity.entities.value.entities.ValueMember;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
//@Component
public class ValueCollectionRunner implements ApplicationRunner {

    private final EntityManager em;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("============= value collection Runner Started =============");
        Address address = new Address("city", "street", "1000");
        ValueMember member = ValueMember.builder()
            .address(address)
            .name("member")
            .build();

        member.getFavoriteFoods().add("치킨");
        member.getFavoriteFoods().add("족발");
        member.getFavoriteFoods().add("피자");

        member.getAddressHistory().add(new Address("old1", "street1", "000"));
        member.getAddressHistory().add(new Address("old2", "street2", "000"));

        em.persist(member);
        log.info("persist is called!");

        em.flush();
        em.clear();

        log.info("============ start ============");
        // 조회하면 값 컬렉션을 제외한 멤버만 가지고 온다, 즉, 값 컬렉션은 다 지연로딩이다
        ValueMember findMember = em.find(ValueMember.class, member.getMemberNo());
        final List<Address> addressHistory = findMember.getAddressHistory();
        for (Address findAddress : addressHistory) {
            System.out.println("address = " + findAddress.getCity());
        }
        System.out.println("address find is done");

        Set<String> favoriteFoods = findMember.getFavoriteFoods();
        for (String favoriteFood : favoriteFoods) {
            System.out.println("favoriteFood = " + favoriteFood);
        }
        System.out.println("food find is done");

        // 값 타입의 세터 메소드를 활성화하고 수정하면 수정 쿼리가 나가긴 한다
        // 하지만 값 타입을 수정 가능하게 하는 것은 부작용이 있으므로 해당 방법은 사용하면 안되고 값을 수정할 때마다 새로 객체를 생성하는 식으로 수정해야 한다, 기존 값을 사용하고 싶을 때는
        // 기존 객체의 게터 메소드를 활용하면 된다
        // 값 타입 컬렉션은 수정할 수도 없고 수정할 때는 갈아끼워야 한다, 자바 기본 값 타입은 수정할 수 없고 사용자 정의 값 타입은 불변 객체로 만들어서 수정 불가능하게

        log.info("============ 컬렉션 수정 start ============");
        findMember.getFavoriteFoods().remove("치킨");
        findMember.getFavoriteFoods().add("한식");

        // 해당 멤버(findMember)와 연관된 값 타입들을 다 지운다
        // 그리고 컬렉션에 있는 데이터들을 모두 다시 저장, 아래 예제의 경우 값 한 개를 지우고 한 개를 넣었는데 2개의 삽입 쿼리가 나가는 것을 확인할 수 있음
        // 즉, 쓰면 안된다! 한식, 치킨은 원하는 것만 삭제되고 삽입이 되는데, 주소 히스토리의 경우 그게 안된다!
        // 값 타입은 식별자가 없기에 값을 변경하고 추적하는 것이 어렵다, 따라서 다 삭제하고 다시 삽입하는 방법을 쓰는 것
        // @OrderColumn 어노테이션을 써서 풀 수 있긴 하지만, 위험해서 추천하지 않음
        // 대안으로, 값 타입 컬렉션 대신에 일대다 관계를 고려
        // 일대다 관계를 위한 엔티티를 만들고, 영속성 전이 + 고아 객체 제거 기능을 사용해서 값 타입 컬렉션 처럼 사용
        // 값 타입을 래핑해서 엔티티로 승격, 실무에서 해당 방법 많이 사용
        // 값 타입 컬렉션은 데이터를 보존하거나 이력 추적 필요가 없는 경우에 사용. 즉, 데이터 중요도가 낮고 단순한 데이터에 대해서 사용
        findMember.getAddressHistory().remove(new Address("old1", "street1", "000"));
        findMember.getAddressHistory().add(new Address("newCity1", "street1", "000"));

    }}
