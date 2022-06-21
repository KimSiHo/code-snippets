/*
 * @(#)CommonResponse 2021-10-18
 *
 * Copyright 2021 Etoos Education. All rights Reserved.
 * Etoos PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package me.bigmonkey.structure.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CommonResponse<T> {
    @Schema(description = "응답 코드", example = "21000")
    private Integer code = 21000;

    @Schema(description = "응답 메시지", example = "ok")
    private String message = "ok";

    @Schema(description = "응답 데이터")
    private T value;

    protected CommonResponse(T value) {
        this.value = value;
    }

    protected CommonResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> CommonResponse<T> successOf(T data) {
        return new CommonResponse<>(data);
    }

    public static CommonResponse<String> successOk() {
        return successOf(Status.OK.name());
    }

    // TODO "error data" 필요 여부
    public static CommonResponse<Void> errorOf(Integer code, String message) {
        return new CommonResponse<>(code, message);
    }

    public enum Status {
        OK, FAIL
    }
}
