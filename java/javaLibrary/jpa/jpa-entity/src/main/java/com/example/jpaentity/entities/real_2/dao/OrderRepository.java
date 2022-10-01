package com.example.jpaentity.entities.real_2.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.example.jpaentity.entities.real_2.domain.Real2Order;

@Repository
public class OrderRepository {

    private final EntityManager em;

    public OrderRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Real2Order> findAll() {
        return em.createQuery("select o from Real2Order o", Real2Order.class)
            .getResultList();
    }

    public List<Real2Order> findAllByString() {
        return em.createQuery("select o from Real2Order o", Real2Order.class)
            .getResultList();
    }

    public List<Real2Order> findAllByString(OrderSearch orderSearch) {

        String jpql = "select o from Real2Order o join o.member m";
        boolean isFirstCondition = true;

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }

        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }

        TypedQuery<Real2Order> query = em.createQuery(jpql, Real2Order.class)
            .setMaxResults(1000);

        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }

        return query.getResultList();
    }

    /**
     * JPA Criteria
     */
    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Object, Object> m = o.join("member", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name =
                cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
        return query.getResultList();
    }

    public List<Real2Order> findAllWithMemberDelivery() {
        return em.createQuery(
            "select o from Real2Order o" +
                " join fetch o.member m" +
                " join fetch o.delivery d", Real2Order.class)
            .getResultList();
    }

    // orderItems 조인하기 전에는 데이터 갯수가 증가하지 않지만 일대 다를 조인하는 순간 데이터가 뻥튀기 된다
    // jpa는 이렇게 db 에서 내려준 데이터를 임의로 중복을 제거해서 줄 지 판단할 수 없으므로 그냥 그대로 내려준다
    // distinct 키워드를 주면 sql에 distinct를 추가해주는 것과 더불어서, (sql distinct로는 중복 제거가 안된다, 되는 경우도 있지만 여기서는 안된다
    // 한 줄이 완전 똑같아야 되서),  jpa에서 자체적으로 id 값이 같으면 '어 똑같네?' 하면서 중복을 제거해준다, 그래서 중복된 order이 제거된다
    // orderItem 엔티티는 중복된 order 엔티티와 같은 줄로 반환되지만 각 엔티티별로 id 값을 중복검사해서 orderItem 엔티티는 제거가 되지 않는 것 같다..?
    public List<Real2Order> findAllWithItem() {
        return em.createQuery(
            "select distinct o from Real2Order o" +
                " join fetch o.member m" +
                " join fetch o.delivery d" +
                " join fetch o.orderItems oi" +
                " join fetch oi.item i", Real2Order.class)
            .getResultList();

        /*return em.createQuery(
            "select o from Real2Order o" +
                " join fetch o.member m" +
                " join fetch o.delivery d" +
                " join fetch o.orderItems oi" +
                " join fetch oi.item i", Real2Order.class)
            .getResultList();*/
    }

    public List<Real2Order> findAllWithMemberDelivery(int offset, int limit) {
        return em.createQuery(
            "select o from Real2Order o" +
                " join fetch o.member m" +
                " join fetch o.delivery d", Real2Order.class)
            .setFirstResult(offset)
            .setMaxResults(limit)
            .getResultList();
    }
}

