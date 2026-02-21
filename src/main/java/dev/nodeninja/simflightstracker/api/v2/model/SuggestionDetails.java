package dev.nodeninja.simflightstracker.api.v2.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SuggestionDetails {
    private String submitter;
    private String title;
    private String description;
    private String simNetwork;
}
