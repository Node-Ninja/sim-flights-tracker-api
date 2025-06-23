package dev.nodeninja.simflightstracker.api.v2.model;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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