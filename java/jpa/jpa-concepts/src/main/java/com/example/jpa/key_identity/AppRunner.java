package com.example.jpa.key_identity;

import java.time.LocalDateTime;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
//@Component
public class AppRunner implements ApplicationRunner {

    //private final TRefreshTokenJpaRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        final TRefreshTokenInfo refreshTokenInfo = TRefreshTokenInfo.builder()
            .seqClientInfo(0L)
            .seqAuthCodeInfo(0L)
            .memberNo(100L)
            .serviceMemberNo(100L)
            .serviceCode(ServiceCode.SVC002)
            .refreshToken("test")
            .expiryDate(LocalDateTime.now())
            .clientId("ABC")
            .build();

        //repository.save(refreshTokenInfo);
    }
}
