package com.example.jpaentity.entities.real_2.domain;

import static javax.persistence.FetchType.LAZY;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Real2Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Real2Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Real2OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Real2Delivery delivery;

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    //==생성 메서드==//
    public static Real2Order createOrder(Real2Member member, Real2Delivery delivery, Real2OrderItem... orderItems) {
        Real2Order order = new Real2Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (Real2OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    public void setDelivery(Real2Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public void addOrderItem(Real2OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
}
