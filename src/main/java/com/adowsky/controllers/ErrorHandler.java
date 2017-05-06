package com.adowsky.controllers;

import com.adowsky.api.ErrorResponse;
import com.adowsky.service.exception.InternalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(value = InternalException.class)
    public ResponseEntity<ErrorResponse> handleInternalResponse(InternalException ex) {
        log.info("Returning error message=[{}] {}", ex.getErrorCode(), ex.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getErrorCode(), ex.getMessage()));
    }
}
