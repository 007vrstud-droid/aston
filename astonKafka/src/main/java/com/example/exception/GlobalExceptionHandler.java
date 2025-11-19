package com.example.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        log.error("Произошла ошибка: ", ex);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Error-Message", ex.getMessage());

        return new ResponseEntity<>("Произошла ошибка на сервере", headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
