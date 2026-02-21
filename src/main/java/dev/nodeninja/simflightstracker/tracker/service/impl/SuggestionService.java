package dev.nodeninja.simflightstracker.tracker.service.impl;

import com.github.f4b6a3.ulid.UlidCreator;
import dev.nodeninja.simflightstracker.api.v2.model.SuggestionDetails;
import dev.nodeninja.simflightstracker.config.ApplicationConfigProperties;
import dev.nodeninja.simflightstracker.tracker.model.Suggestion;
import dev.nodeninja.simflightstracker.tracker.repository.SuggestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class SuggestionService {
    private final ApplicationConfigProperties configProps;
    private final SuggestionRepository suggestionRepository;

    public boolean submitSuggestion(SuggestionDetails details) {

        try {
            var recordId = UlidCreator.getUlid();

            var record  = Suggestion.builder()
                    .suggestionId(recordId.toString())
                    .submitter(details.getSubmitter())
                    .title(details.getTitle())
                    .description(details.getDescription())
                    .simNetwork(details.getSimNetwork())
                    .status(Suggestion.Status.OPEN)
                    .createdAt(Instant.now())
                    .build();

            var created = suggestionRepository.save(record);

            log.debug("Log created with id: {}", created.getSuggestionId());
            return true;

        } catch (Exception e) {
            log.error("Something went wrong while submitting suggestion", e);
            return false;
        }
    }
}
