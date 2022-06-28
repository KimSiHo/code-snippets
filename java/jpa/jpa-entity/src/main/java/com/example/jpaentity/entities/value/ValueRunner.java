package com.example.jpaentity.entities.value;

import java.rmi.dgc.VMID;

import javax.persistence.EntityManager;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpaentity.entities.mapping.mappedSuperClass.MscMember;
import com.example.jpaentity.entities.value.entities.Address;
import com.example.jpaentity.entities.value.entities.ValueMember;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
//@Component
public class ValueRunner implements ApplicationRunner {

    private final EntityManager em;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("============= value Runner Started =============");

        // 기본 사용법
        /*ValueMember member = ValueMember.createMember();
        em.persist(member);*/

        // 값 타입은 공유하면 안 됨, 이렇게 하면 업데이트 쿼리가 2번 나간다
        Address address = new Address("city", "street", "1000");
        ValueMember member = ValueMember.builder()
            .address(address)
            .name("member")
            .build();
        em.persist(member);

        // 이런식으로 복사본을 만들어서 복사본을 설정해 주어야 된다, 임베디드 타입은 '전부 다' 불변 객체로 만들어서 공유로 인한 부작용을 미리 차단하자!
        // 따라서, 값 타입은 동일성 비교가 아니라 동등성 비교를 해야 하며 equals 메소드를 오버라이드 해야 된다
        //Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());
        ValueMember member1 = ValueMember.builder()
            .address(address)
            .name("member1")
            .build();
        em.persist(member1);

        member.getAddress().changeCity("change-city");
    }
}
