package com.darmokhval.CarManagementService.exception;

public class JwtValidationException extends RuntimeException{

    public JwtValidationException(String message) {
        super(message);
    }

}
