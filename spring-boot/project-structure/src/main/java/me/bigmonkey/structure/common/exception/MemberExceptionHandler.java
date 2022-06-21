/*
 * Copyright 2021 Etoos Education. All rights Reserved.
 * Etoos PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package me.bigmonkey.structure.common.exception;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class MemberExceptionHandler {
    private static final String ERROR_LOG_MSG =
            "[REQUEST (Method: %s) (URI: %s)] [ERROR (Type: %s) (Message: %s)]";

    @ExceptionHandler({MemberServiceException.class})
    public ResponseEntity<CommonResponse<Void>> serviceException(
            HttpServletRequest request, MemberServiceException ex) {
        log.error(getExceptionLog(request, ex), ex);

        return buildErrorResponse(ex.getErrorCode());
//        return buildErrorResponse(ex.getErrorCode(), ex.getMessage());
    }

    /**
     * Method not allowed
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<CommonResponse<Void>> httpRequestMethodNotSupportedException(
            HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {
        log.error(getExceptionLog(request, ex), ex);

        return buildErrorResponse(MemberErrorCode.METHOD_NOT_ALLOWED);
    }

    /**
     * Unsupported media type
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<CommonResponse<Void>> httpMediaTypeNotSupportedException(
            HttpServletRequest request, HttpMediaTypeNotSupportedException ex) {
        log.error(getExceptionLog(request, ex), ex);

        return buildErrorResponse(MemberErrorCode.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * PathVariable / RequestParam
     * Failed to convert value of type
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<CommonResponse<Void>> methodArgumentTypeMismatchException(
            HttpServletRequest request, MethodArgumentTypeMismatchException ex) {
        log.error(getExceptionLog(request, ex), ex);

        return buildErrorResponse(MemberErrorCode.BAD_REQUEST_VALIDATION);
    }

    /**
     * PathVariable / RequestParam
     * Missing request value
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler({MissingServletRequestParameterException.class, MissingPathVariableException.class})
    public ResponseEntity<CommonResponse<Void>> missingServletRequestParameterException(
            HttpServletRequest request, MissingRequestValueException ex) {
        log.error(getExceptionLog(request, ex), ex);

        return buildErrorResponse(MemberErrorCode.BAD_REQUEST_VALIDATION);
    }

    /**
     * PathVariable / RequestParam
     * Failed validation
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<CommonResponse<Void>> constraintViolationException(
            HttpServletRequest request, ConstraintViolationException ex) {
        log.error(getExceptionLog(request, ex), ex);

        return buildErrorResponse(MemberErrorCode.BAD_REQUEST_VALIDATION);
    }

    /**
     * ModelAttribute
     * Failed validation
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler({BindException.class})
    public ResponseEntity<CommonResponse<Void>> bindException(
            HttpServletRequest request, BindException ex) {
        log.error(getExceptionLog(request, ex), ex);

        return buildErrorResponse(MemberErrorCode.BAD_REQUEST_VALIDATION);
    }

    /**
     * RequestBody
     * Failed to convert value of type
     * Missing request value
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<CommonResponse<Void>> httpMessageNotReadableException(
            HttpServletRequest request, HttpMessageNotReadableException ex) {
        log.error(getExceptionLog(request, ex), ex);

        return buildErrorResponse(MemberErrorCode.BAD_REQUEST_VALIDATION);
    }

    /**
     * RequestBody
     * Failed validation
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<CommonResponse<Void>> methodArgumentNotValidException(
            HttpServletRequest request, MethodArgumentNotValidException ex) {
        log.error(getExceptionLog(request, ex), ex);

        return buildErrorResponse(MemberErrorCode.BAD_REQUEST_VALIDATION);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<CommonResponse<Void>> unknownException(
            HttpServletRequest request, Exception ex) {
        log.error(getExceptionLog(request, ex), ex);

        return buildErrorResponse(MemberErrorCode.UNKNOWN_SERVER_ERROR);
    }

    private ResponseEntity<CommonResponse<Void>> buildErrorResponse(MemberErrorCode errorCode) {
        return buildErrorResponse(errorCode, errorCode.getMessage());
    }

    private ResponseEntity<CommonResponse<Void>> buildErrorResponse(MemberErrorCode errorCode, String errorMessage) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.errorOf(errorCode.getCode(), errorMessage));
    }

    private String getExceptionLog(HttpServletRequest request, Exception exception) {
        return String.format(
                ERROR_LOG_MSG,
                request.getMethod(),
                request.getRequestURI(),
                exception.getClass().getSimpleName(),
                exception.getMessage());
    }
}
