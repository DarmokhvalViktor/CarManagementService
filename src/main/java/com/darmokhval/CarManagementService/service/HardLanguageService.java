package com.darmokhval.CarManagementService.service;

import com.darmokhval.CarManagementService.model.dto.InappropriateWordDTO;
import com.darmokhval.CarManagementService.model.entity.InappropriateWord;
import com.darmokhval.CarManagementService.repository.HardLanguageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HardLanguageService {
    private final HardLanguageRepository hardLanguageRepository;

    public List<InappropriateWordDTO> getAllWords() {
        List<InappropriateWord> words = hardLanguageRepository.findAll();
        return words
                .stream()
                .map(word -> new InappropriateWordDTO(word.getContent()))
                .toList();
    }

    @Transactional
    public InappropriateWordDTO saveWord(InappropriateWordDTO wordDTO) {
        return new InappropriateWordDTO(hardLanguageRepository.save(new InappropriateWord(wordDTO.getContent())).getContent());
    }
}
