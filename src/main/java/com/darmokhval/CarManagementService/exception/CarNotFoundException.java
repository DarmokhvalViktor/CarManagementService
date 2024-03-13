package com.darmokhval.CarManagementService.exception;

public class CarNotFoundException extends RuntimeException{
    public CarNotFoundException(Long id) {
        super(String.format("Car with id %d was not found", id));
    }
}
