package dev.nodeninja.simflightstracker.api.v2.controller;

import dev.nodeninja.simflightstracker.api.v2.model.SuggestionDetails;
import dev.nodeninja.simflightstracker.tracker.service.impl.SuggestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/suggestions")
public class SuggestionController {
    private final SuggestionService suggestionService;

    @PostMapping
    public Boolean submitSuggestion(@RequestBody SuggestionDetails details) {
        return suggestionService.submitSuggestion(details);
    }
}
