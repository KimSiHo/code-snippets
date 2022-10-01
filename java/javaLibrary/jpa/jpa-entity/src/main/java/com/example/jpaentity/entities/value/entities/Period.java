package com.example.jpaentity.entities.value.entities;

import java.time.LocalDateTime;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Period {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
