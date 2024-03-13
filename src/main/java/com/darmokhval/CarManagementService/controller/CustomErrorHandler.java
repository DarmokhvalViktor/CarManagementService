package com.darmokhval.CarManagementService.controller;

import com.darmokhval.CarManagementService.exception.CarNotFoundException;
import com.darmokhval.CarManagementService.exception.JwtValidationException;
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
        return createResponse(exception, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({PasswordMismatchException.class})
    public ResponseEntity<ErrorDTO> handlePasswordMismatchException(PasswordMismatchException exception) {
        return createResponse(exception, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorDTO> handleIllegalArgumentException(IllegalArgumentException exception) {
        return createResponse(exception, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({CarNotFoundException.class})
    public ResponseEntity<ErrorDTO> handleCarNotFoundException(CarNotFoundException exception) {
        return createResponse(exception, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({JwtValidationException.class})
    public ResponseEntity<ErrorDTO> handleJwtValidationException(JwtValidationException exception) {
        return createResponse(exception, HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<ErrorDTO> createResponse(Exception exception, HttpStatus status) {
        String details = exception.getMessage();
        return ResponseEntity
                .status(status)
                .body(ErrorDTO.builder()
                        .timestamp(System.currentTimeMillis())
                        .details(details)
                        .build());
    }
}
