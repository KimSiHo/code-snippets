/*
 * Copyright 2021 Etoos Education. All rights Reserved.
 * Etoos PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package me.bigmonkey.structure.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * code > 2(member) + 1(group) + XXX(index)
 */
@Getter
@AllArgsConstructor
public enum MemberErrorCode {

    METHOD_NOT_ALLOWED(24001, "method not allowed"),
    UNSUPPORTED_MEDIA_TYPE(24002, "unsupported media type"),

    // Common
    BAD_REQUEST_CLIENT_IP(21001, "client ip is wrong"),

    // Sing Up
    SING_UP_INVALID_TOKEN(22001, "sign up token is invalid"),

    // User
    USER_NOT_FOUND(23001, "not found user"),

    // Service
    SERVICE_ALREADY_REGISTERED(24001, "already registered"),

    // Utils
    UTILS_FAILED_TO_SEND_SMS(26001, "failed to send sms"),

    UNKNOWN_SERVER_ERROR(29999, "unknown server error");

    private final Integer code;
    private final String message;
}
