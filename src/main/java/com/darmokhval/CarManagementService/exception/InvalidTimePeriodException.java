package com.darmokhval.CarManagementService.exception;

public class InvalidTimePeriodException extends RuntimeException{
    public InvalidTimePeriodException(String period) {
        super(String.format("Invalid time period format, %s", period));
    }
}
