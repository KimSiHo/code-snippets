package com.example.jpaentity.entities.cascade;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpaentity.entities.cascade.entities.Child;
import com.example.jpaentity.entities.cascade.entities.Parent;
import com.example.jpaentity.entities.manyToOne.entities.MtoMember;
import com.example.jpaentity.entities.manyToOne.entities.MtoSocialInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
//@Component
public class CascadeRunner implements ApplicationRunner {

    private final EntityManager em;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("============= cascade Runner Started =============");

        Parent parent = Parent.createParent();
        Child child = Child.createChild();
        Child child1 = Child.createChild();
        Child child2 = Child.createChild();

        parent.addChild(child);
        parent.addChild(child1);
        parent.addChild(child2);
        em.persist(parent);

        em.flush();
        em.clear();

        // CascadeType.ALL + orphanRemovel=true를 통해서 DDD의 aggregate Root 개념을 구현할 때 유용
        // 부모 객체를 통해서 자식의 생명주기를 관리하기에 DAO나 repository가 없어도 된다
        Parent findParent = em.find(Parent.class, parent.getId());
        findParent.getChildList().remove(0);
    }
}
