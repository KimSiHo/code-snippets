package com.example.jpaentity.entities.real_2.dao;

import java.time.LocalDateTime;

import com.example.jpaentity.entities.real_2.domain.OrderStatus;
import com.example.jpaentity.entities.real_2.domain.Real2Address;

import lombok.Data;

@Data
public class OrderSimpleQueryDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate; //주문시간
    private OrderStatus orderStatus;
    private Real2Address address;

    public OrderSimpleQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Real2Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}
