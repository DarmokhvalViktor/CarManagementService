package com.darmokhval.CarManagementService.controller;

import com.darmokhval.CarManagementService.model.dto.InappropriateWordDTO;
import com.darmokhval.CarManagementService.service.HardLanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HardLanguageController {
    private final HardLanguageService hardLanguageService;

    @GetMapping("api/bad_words")
    public ResponseEntity<List<InappropriateWordDTO>> getAllWords() {
        return ResponseEntity.ok(hardLanguageService.getAllWords());
    }
    @PostMapping("api/bad_words")
    public ResponseEntity<InappropriateWordDTO> saveBadWord(@RequestBody InappropriateWordDTO wordDTO) {
        return ResponseEntity.ok(hardLanguageService.saveWord(wordDTO));
    }
}
