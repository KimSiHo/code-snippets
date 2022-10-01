package com.example.jpaentity.entities.real_2;

import javax.persistence.EntityManager;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class Real2Runner implements ApplicationRunner {

    private final EntityManager em;

    // 엔티티에서 레이징 로딩인것은 무시해라!
    @Bean
    Hibernate5Module hibernate5Module() {
        Hibernate5Module hibernate5Module = new Hibernate5Module();
        //강제 지연 로딩 설정
        //hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true);
        return hibernate5Module;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("============= Real 2 Runner Started =============");



    }
}
