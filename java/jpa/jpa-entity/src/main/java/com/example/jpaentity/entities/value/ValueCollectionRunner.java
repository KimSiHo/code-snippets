package com.example.jpaentity.entities.value;

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
@Component
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
        em.persist(member);

    }
}
