package com.darmokhval.CarManagementService.exception;

public class ForbiddenWordException extends RuntimeException{
    public ForbiddenWordException(String word) {
        super(String.format("You cannot use forbidden word, %s", word));
    }
}
