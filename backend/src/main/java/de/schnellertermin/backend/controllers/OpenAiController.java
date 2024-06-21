package de.schnellertermin.backend.controllers;

import de.schnellertermin.backend.services.concretes.OpenAiManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/open-api")
@RequiredArgsConstructor
public class OpenAiController {
    private final OpenAiManager openAiManager;

    @GetMapping
    public String evaluateImage(@RequestParam String imageUrl,@RequestParam String description) {
        try {
            return openAiManager.sendRequest(imageUrl, description);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
