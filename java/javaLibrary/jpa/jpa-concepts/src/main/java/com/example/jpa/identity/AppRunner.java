package com.example.jpa.identity;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import com.example.jpa.identity.entities.IdentityMember;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
//@Component
public class AppRunner implements ApplicationRunner {

    //private final IdentityMemberJpaRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("============= Merge Runner Started =============");

        final IdentityMember identityMember = IdentityMember.builder()
            .name("test 1")
            .build();

        //repository.save(identityMember);
    }
}
