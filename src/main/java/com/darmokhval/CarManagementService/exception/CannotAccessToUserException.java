package com.darmokhval.CarManagementService.exception;

public class CannotAccessToUserException extends RuntimeException{
    public CannotAccessToUserException(Long id) {
        super(String.format("Cannot access to user %s data", id));
    }
}
