package com.example.jpaentity.entities.join.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
//@Entity
//@DiscriminatorValue("M") // 기본으로 엔티티 이름이 들어가지만 위와 같은 방식으로 들어가는 값을 커스터마이징 할 수 있다
public class Movie extends Item {

    private String director;
    private String actor;

    public static Movie createMovie() {
        Movie movie = new Movie("direct", "actor");
        movie.setName("item-name");
        movie.setPrice(1000);
        return movie;
    }
}
