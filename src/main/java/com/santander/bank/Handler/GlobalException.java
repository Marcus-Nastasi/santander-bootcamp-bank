package com.santander.bank.Handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    //private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleUnexpectedException(Throwable throwable) {
        String errorMessage = "{\"error\": " + "\"" + throwable.getMessage() + "\"" + '}';
        //logger.error(errorMessage, throwable);
        return ResponseEntity.badRequest().body(errorMessage);
    }
}




