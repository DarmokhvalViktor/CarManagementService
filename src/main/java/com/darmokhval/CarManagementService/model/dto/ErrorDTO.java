package com.darmokhval.CarManagementService.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTO {
    private Long timestamp;
    private String details;
}
