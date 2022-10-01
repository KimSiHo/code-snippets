package com.example.jpaentity.entities.join;

import javax.persistence.EntityManager;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpaentity.entities.join.entities.Book;
import com.example.jpaentity.entities.join.entities.Item;
import com.example.jpaentity.entities.join.entities.Movie;
import com.example.jpaentity.entities.oneToOne.entities.OtoMember;
import com.example.jpaentity.entities.oneToOne.entities.OtoTeam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
//@Component
public class JoinRunner implements ApplicationRunner {

    private final EntityManager em;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("============= join Runner Started =============");

        final Movie movie = Movie.createMovie();
        em.persist(movie);

    }
}
