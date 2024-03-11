package com.darmokhval.CarManagementService.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String name) {
        super(String.format("Entity with name %s was not found", name));
    }
    public EntityNotFoundException(Long id) {
        super(String.format("Entity with id %d was not found", id));
    }
}
