package com.darmokhval.CarManagementService.exception;

public class PasswordMismatchException extends RuntimeException{
    public PasswordMismatchException() {
        super("Password mismatch");
    }
}
