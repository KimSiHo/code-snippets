package com.example.jpaentity.entities.real_2.domain;

import com.example.jpaentity.entities.real_2.domain.item.Real2Item;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Real2OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Real2Item item;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Real2Order order;

    private int orderPrice; //주문 가격
    private int count; //주문 수량

    //==생성 메서드==//
    public static Real2OrderItem createOrderItem(Real2Item item, int orderPrice, int count) {
        Real2OrderItem orderItem = new Real2OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }
}
