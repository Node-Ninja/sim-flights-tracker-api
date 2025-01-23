package dev.nodeninja.simflightstracker.api.v2.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
public class VatsimFlightHistoryItem {
    private Long id;
    private Integer type;
    private Integer rating;
    private String callsign;
    private OffsetDateTime start;
    private OffsetDateTime end;
    private String server;
}