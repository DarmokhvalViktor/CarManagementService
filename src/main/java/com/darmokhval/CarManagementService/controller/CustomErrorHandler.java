package com.darmokhval.CarManagementService.controller;

import com.darmokhval.CarManagementService.exception.PasswordMismatchException;
import com.darmokhval.CarManagementService.exception.EntityNotFoundException;
import com.darmokhval.CarManagementService.model.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomErrorHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ErrorDTO> handleUserNotFoundException(EntityNotFoundException exception) {
        return createResponse(exception);
    }
    @ExceptionHandler({PasswordMismatchException.class})
    public ResponseEntity<ErrorDTO> handlePasswordMismatchException(PasswordMismatchException exception) {
        return createResponse(exception);
    }
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorDTO> handleIllegalArgumentException(IllegalArgumentException exception) {
        return createResponse(exception);
    }

    private ResponseEntity<ErrorDTO> createResponse(Exception exception) {
        String details = exception.getMessage();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorDTO.builder()
                        .timestamp(System.currentTimeMillis())
                        .details(details)
                        .build());
    }
}
