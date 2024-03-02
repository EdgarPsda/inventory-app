package com.psdadev.stock.stockapp.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException {

    private String code;
    private HttpStatus status;

    public ResourceNotFoundException(String code, HttpStatus status, String message) {
        super(message);
        this.code = code;
        this.status = status;
    }

}
