package com.example.jpaentity.entities.real_2;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpaentity.entities.real_2.domain.Real2Address;
import com.example.jpaentity.entities.real_2.domain.Real2Delivery;
import com.example.jpaentity.entities.real_2.domain.Real2Member;
import com.example.jpaentity.entities.real_2.domain.Real2Order;
import com.example.jpaentity.entities.real_2.domain.Real2OrderItem;
import com.example.jpaentity.entities.real_2.domain.item.Real2Book;

import lombok.RequiredArgsConstructor;

/**
 * 종 주문 2개 * userA * JPA1 BOOK * JPA2 BOOK * userB * SPRING1 BOOK * SPRING2 BOOK
 */
@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        //initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            System.out.println("Init1" + this.getClass());
            Real2Member member = createMember("userA", "서울", "1", "1111");
            em.persist(member);

            Real2Book book1 = createBook("JPA1 BOOK", 10000, 100);
            em.persist(book1);

            Real2Book book2 = createBook("JPA2 BOOK", 20000, 100);
            em.persist(book2);

            Real2OrderItem orderItem1 = Real2OrderItem.createOrderItem(book1, 10000, 1);
            Real2OrderItem orderItem2 = Real2OrderItem.createOrderItem(book2, 20000, 2);

            Real2Delivery delivery = createDelivery(member);
            Real2Order order = Real2Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private Real2Member createMember(String name, String city, String street, String zipcode) {
            Real2Member member = new Real2Member();
            member.setName(name);
            member.setAddress(new Real2Address(city, street, zipcode));
            return member;
        }

        private Real2Book createBook(String name, int price, int stockQuantity) {
            Real2Book book1 = new Real2Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            return book1;
        }

        private Real2Delivery createDelivery(Real2Member member) {
            Real2Delivery delivery = new Real2Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        public void dbInit2() {
            Real2Member member = createMember("userB", "진주", "2", "2222");
            em.persist(member);

            Real2Book book1 = createBook("SPRING1 BOOK", 20000, 200);
            em.persist(book1);

            Real2Book book2 = createBook("SPRING2 BOOK", 40000, 300);
            em.persist(book2);

            Real2OrderItem orderItem1 = Real2OrderItem.createOrderItem(book1, 20000, 3);
            Real2OrderItem orderItem2 = Real2OrderItem.createOrderItem(book2, 40000, 4);

            Real2Delivery delivery = createDelivery(member);
            Real2Order order = Real2Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }
    }
}

