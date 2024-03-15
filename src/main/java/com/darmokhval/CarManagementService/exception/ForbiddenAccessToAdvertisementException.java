package com.darmokhval.CarManagementService.exception;

public class ForbiddenAccessToAdvertisementException extends RuntimeException {
    public ForbiddenAccessToAdvertisementException() {
        super("Cannot access to advertisement.");
    }
}
