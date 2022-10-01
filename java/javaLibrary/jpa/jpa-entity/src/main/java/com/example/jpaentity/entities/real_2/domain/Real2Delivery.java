package com.example.jpaentity.entities.real_2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Real2Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Real2Order order;

    @Embedded
    private Real2Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //READY, COMP
}
