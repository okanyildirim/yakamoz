package com.hof.yakomozauth.common;

import com.hof.yakomozauth.common.exception.AuthBusinessException;
import com.hof.yakomozauth.common.exception.AuthRunTimeException;
import com.hof.yakomozauth.common.exception.NotFoundException;
import com.hof.yakomozauth.data.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthRunTimeException.class)
    ResponseEntity handleAuthRuntimeException(AuthRunTimeException ex, Locale locale){
        log.error("Auth Runtime Exception. errorCode {}, message {}", ex.getErrorCode(), ex.getMessage(),ex);

        Integer errorCode = ex.getErrorCode();

        if (errorCode == null){
            errorCode =1000;
        }

        return ResponseEntity.ok(new ErrorResponse(ex.getErrorCode(),ex.getMessage()));
    }

    @ExceptionHandler(AuthBusinessException.class)
    ResponseEntity handleAuthBusinessException(AuthBusinessException ex, Locale locale){
        log.error("AuthBsBusiness Exception. errorCode {}, message {}", ex.getErrorCode(), ex.getMessage(),ex);

        Integer errorCode = ex.getErrorCode();

        if (errorCode == null){
            errorCode =1000;
        }

        return ResponseEntity.ok(new ErrorResponse(ex.getErrorCode(),ex.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity handleNotFoundException(NotFoundException ex, Locale locale){
        log.error("Not Found Exception. errorCode {}, message {}", ex.getErrorCode(), ex.getMessage(),ex);

        Integer errorCode = ex.getErrorCode();

        if (errorCode == null){
            errorCode =1000;
        }

        return ResponseEntity.ok(new ErrorResponse(ex.getErrorCode(),ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity handleException(Exception ex, Locale locale){
        log.error("Unhandled exception is happened!",ex);

        return ResponseEntity.ok(new ErrorResponse(1000, "Unexpected Error!"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity handleIllegalArgumentException(IllegalArgumentException ex, Locale locale){
        log.error("Illegal Argument exception is happened!",ex);

        return ResponseEntity.ok(new ErrorResponse(1000, ex.getMessage()));
    }

}
