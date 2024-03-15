package com.darmokhval.CarManagementService.exception;

public class InvalidRefreshTokenSignatureException extends RuntimeException {
    public InvalidRefreshTokenSignatureException() {
        super("Token signature doesn't match refresh token or issuer is not supported ");
    }
}
