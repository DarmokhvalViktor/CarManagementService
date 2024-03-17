package com.darmokhval.CarManagementService.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class MessageDTO {
    private Long id;
    private Long senderId;
    private Long recipientId;
    private String content;
    private LocalDateTime timestamp;
}
