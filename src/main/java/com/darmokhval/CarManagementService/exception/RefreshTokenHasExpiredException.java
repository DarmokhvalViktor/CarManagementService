package com.darmokhval.CarManagementService.exception;

public class RefreshTokenHasExpiredException extends RuntimeException{
    public RefreshTokenHasExpiredException() {
        super("Refresh token has expired");
    }
}
