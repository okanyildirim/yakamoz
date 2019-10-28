package com.hof.cms.common;

import com.hof.cms.common.exception.AuthBusinessException;
import com.hof.cms.common.exception.AuthRunTimeException;
import com.hof.cms.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
         ex.getBindingResult().getAllErrors()
                 .forEach(objectError -> {
                     String fieldName = ((FieldError) objectError).getField();
                     String errorMessage = objectError.getDefaultMessage();
                     errors.put(fieldName,errorMessage);
                 });
        return ResponseEntity.ok(errors.toString());
    }

}
