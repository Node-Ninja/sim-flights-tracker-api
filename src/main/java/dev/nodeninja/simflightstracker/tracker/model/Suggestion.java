package dev.nodeninja.simflightstracker.tracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "suggestions")
public class Suggestion {
    @Id
    private String suggestionId;
    private String simNetwork;
    private String title;
    private String description;
    private String submitter;
    private Status status;
    @CreatedDate
    private Instant createdAt = Instant.now();
    @LastModifiedDate
    private Instant updatedAt = null;

    public static enum Status {
        OPEN, IN_PROGRESS, COMPLETED, REJECTED
    }
}
