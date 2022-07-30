/*
 * @(#)RedisCacheNames 2021-10-26
 *
 * Copyright 2021 Etoos Education. All rights Reserved.
 * Etoos PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.etoos.member.common.cache;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisCacheNames {

    // 키 구분자
    public static final String REDIS_CACHE_KEY_SEPARATOR = ":";

    // 회원 가입 요청 전까지의 데이터를 캐싱
    public static final String SIGN_UP_TOKEN = "SIGN-UP-TOKEN";

    // 회원 가입 요청 전까지의 데이터를 캐싱
    public static final String SEND_RETRY = "SEND-RETRY";

    // 이투스 회원번호 업데이트를 위한 데이터를 캐싱
    public static final String ETOOS_TOKEN = "ETOOS-TOKEN";

    // 회원 마스터 정보 캐싱.
    public static final String MEMBER = "MEMBER";

    // 서비스별 가입 동의 테이블 캐싱.
    public static final String SERVICE_TERMS_AGREE = "SERVICE_TERMS_AGREE";

    // 서비스별 프로필 정보 테이블 캐싱.
    public static final String SERVICE_PROFILE = "SERVICE_PROFILE";

    public static final String SERVICE_PROFILE_ETC = "SERVICE_PROFILE_ETC";

    public static final String MEMBER_BLOCK = "BLOCK_USER";

    // 비밀번호 인증 데이터를 캐싱
    public static final String PASSWORD_TOKEN = "PASSWORD_TOKEN";

    // 다중 ID 이용 캐시
    public static final String MEMBERS = "MEMBERS";

    // AUTH 서버에서 로그인 처리에 이용하는 캐시
    public static final String MEMBER_ID = "MEMBER-ID";

    // 비밀번호 실패 횟수
    public static final String PWD_FAIL_COUNT = "PWD-FAIL-COUNT";

    public static final String SESSION_TOKEN = "ST";

    public static final String CLIENT_INFO = "CLIENT_INFO";

    public static final String MEMBER_INFO = "MEMBER_INFO";

    // 30일 이내 변경 이력 여부 체크
    public static final String CHANGE_PHONE_ID = "CHANGE_PHONE_ID";
}
