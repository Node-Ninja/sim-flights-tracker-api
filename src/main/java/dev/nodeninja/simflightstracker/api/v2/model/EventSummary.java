package dev.nodeninja.simflightstracker.api.v2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
public class EventSummary {
    private Integer id;
    private String name;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
}
