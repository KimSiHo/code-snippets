package me.bigmonkey.structure.common.exception;

import lombok.Getter;

@Getter
public class MemberServiceException extends RuntimeException {

    private final MemberErrorCode errorCode;
    private final String message;

    public MemberServiceException(MemberErrorCode errorCode) {
        super(errorCode.getMessage());

        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    public MemberServiceException(MemberErrorCode errorCode, String errorMessage) {
        super(errorMessage);

        this.errorCode = errorCode;
        this.message = errorMessage;
    }

    public MemberServiceException(MemberErrorCode errorCode, String errorMessage, Throwable cause) {
        super(errorMessage, cause);

        this.errorCode = errorCode;
        this.message = errorMessage;
    }
}
