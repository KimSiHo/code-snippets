package com.example.jpaentity.entities.real_2.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager em;

    // 이렇게 select 절 최적화하는 쿼리문은 별도의 repository를 만들어서 정의한다
    // 기본 repository는 순수한 엔티티를 조회하는 데 사용하고 query repository는 최적화를 위한 repository 용으로 사용한다
    public List<OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery(
                "select new com.example.jpaentity.entities.real_2.dao.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                        " from Real2Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderSimpleQueryDto.class)
                .getResultList();
    }
}