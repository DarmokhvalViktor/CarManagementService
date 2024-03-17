package com.darmokhval.CarManagementService.service;

import com.darmokhval.CarManagementService.exception.EntityNotFoundException;
import com.darmokhval.CarManagementService.mapper.MainMapper;
import com.darmokhval.CarManagementService.model.dto.MessageDTO;
import com.darmokhval.CarManagementService.model.entity.Advertisement;
import com.darmokhval.CarManagementService.model.entity.Message;
import com.darmokhval.CarManagementService.repository.AdvertisementRepository;
import com.darmokhval.CarManagementService.repository.MessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final AdvertisementRepository advertisementRepository;
    private final MainMapper mainMapper;

    public List<MessageDTO> getMessagesForCar(Long carId) {
        Advertisement advertisement =  advertisementRepository.findById(carId).orElseThrow(() -> new EntityNotFoundException(carId));
        List<Message> messages = advertisement.getMessages();
        return messages.stream().map(mainMapper::messageEntityToDTO).toList();
    }
    @Transactional
    public MessageDTO sendMessage(Long carId, MessageDTO messageDTO) {
        Advertisement advertisement = advertisementRepository.findById(carId).orElseThrow(() -> new EntityNotFoundException(carId));
        Message message = new Message();
        message.setSenderId(messageDTO.getSenderId());
        message.setRecipientId(messageDTO.getRecipientId());
        message.setContent(messageDTO.getContent());
        message.setTimestamp(messageDTO.getTimestamp());
        message.setAdvertisement(advertisement);
        Message savedMessage = messageRepository.save(message);
        return mainMapper.messageEntityToDTO(savedMessage);
    }
}
