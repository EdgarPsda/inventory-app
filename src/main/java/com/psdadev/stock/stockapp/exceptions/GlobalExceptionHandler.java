package com.psdadev.stock.stockapp.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.psdadev.stock.stockapp.model.dto.ErrorDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @SuppressWarnings("null")
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> handleResourceNotFoundException(ResourceNotFoundException ex) {

        ErrorDto errorDetails = ErrorDto.builder().code(ex.getCode()).message(ex.getMessage()).build();

        return new ResponseEntity<>(errorDetails, ex.getStatus());
    }

}
