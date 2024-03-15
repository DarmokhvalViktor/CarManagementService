package com.darmokhval.CarManagementService.controller;

import com.darmokhval.CarManagementService.exception.*;
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
    @ExceptionHandler({RefreshTokenHasExpiredException.class})
    public ResponseEntity<ErrorDTO> handleRefreshTokenHasExpiredException(RefreshTokenHasExpiredException exception) {
        return createResponse(exception, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({JwtValidationException.class})
    public ResponseEntity<ErrorDTO> handleJwtValidationException(JwtValidationException exception) {
        return createResponse(exception, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler({InvalidRefreshTokenSignatureException.class})
    public ResponseEntity<ErrorDTO> handleInvalidRefreshTokenSignatureException(InvalidRefreshTokenSignatureException exception) {
        return createResponse(exception, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler({MaximumNumberOfAdsException.class})
    public ResponseEntity<ErrorDTO> handleMaximumNumberOfAdsException(MaximumNumberOfAdsException exception) {
        return createResponse(exception, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({ForbiddenWordException.class})
    public ResponseEntity<ErrorDTO> handleForbiddenWordException(ForbiddenWordException exception) {
        return createResponse(exception, HttpStatus.BAD_REQUEST);
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
