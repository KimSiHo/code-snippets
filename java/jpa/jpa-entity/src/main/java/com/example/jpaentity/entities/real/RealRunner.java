package com.example.jpaentity.entities.real;

import javax.persistence.EntityManager;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class RealRunner implements ApplicationRunner {

    private final EntityManager em;

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
