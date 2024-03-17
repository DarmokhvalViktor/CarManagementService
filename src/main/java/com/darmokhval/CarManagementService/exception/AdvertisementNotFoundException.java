package com.darmokhval.CarManagementService.exception;

public class AdvertisementNotFoundException extends RuntimeException{
    public AdvertisementNotFoundException(Long id) {
        super(String.format("Car with id %d was not found", id));
    }
}
