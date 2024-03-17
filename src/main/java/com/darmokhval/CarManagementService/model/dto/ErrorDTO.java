package com.darmokhval.CarManagementService.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema
public class ErrorDTO {
    private Long timestamp;
    private String details;
}
