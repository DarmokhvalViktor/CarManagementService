package com.darmokhval.CarManagementService.controller;

import com.darmokhval.CarManagementService.model.dto.MessageDTO;
import com.darmokhval.CarManagementService.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/messages/advertisement/{carId}")
    public ResponseEntity<List<MessageDTO>> getMessagesForAdvertisement(@PathVariable Long carId) {
        return ResponseEntity.ok(messageService.getMessagesForCar(carId));
    }

    @PostMapping("/messages/advertisement/{carId}")
    public ResponseEntity<MessageDTO> sendMessage(@PathVariable Long carId, @RequestBody MessageDTO messageDTO) {
        return ResponseEntity.ok(messageService.sendMessage(carId, messageDTO));
    }
}
