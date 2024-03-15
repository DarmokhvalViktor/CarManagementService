package com.darmokhval.CarManagementService.exception;

import jakarta.validation.constraints.NotBlank;

public class MaximumNumberOfAdsException extends RuntimeException {
    public MaximumNumberOfAdsException() {
        super("You have to upgrade your account to premium status to be able to post more than one advertisement.");
    }
}
