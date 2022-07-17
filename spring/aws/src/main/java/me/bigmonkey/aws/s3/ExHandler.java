package me.bigmonkey.aws.s3;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RestControllerAdvice
public class ExHandler {

    /**
     * 파일 업로드 용량 초과시 발생
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    protected ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(
        MaxUploadSizeExceededException e) {
        log.info("handleMaxUploadSizeExceededException", e);

        ErrorResponse response = ErrorResponse.of(400);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @Builder
    @Data
    public static class ErrorResponse {
        Integer errorCode;

        public static ErrorResponse of(Integer errorCode) {
            return ErrorResponse.builder()
                .errorCode(errorCode)
                .build();
        }
    }
}
