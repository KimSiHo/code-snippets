package me.bigmoneky.mybatis.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class MemberInfoRequest {
    private LocalDateTime start;
    private LocalDateTime end;
}