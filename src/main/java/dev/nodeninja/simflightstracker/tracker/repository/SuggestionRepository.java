package dev.nodeninja.simflightstracker.tracker.repository;

import dev.nodeninja.simflightstracker.tracker.model.Suggestion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuggestionRepository extends MongoRepository<Suggestion, String> {
}
