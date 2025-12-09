package dev.nodeninja.simflightstracker.api.v2.model;

import dev.nodeninja.simflightstracker.tracker.model.ControllerType;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AtcSummary {
    private String callsign;
    private String frequency;
    private ControllerType facility;
    @Nullable
    private Double latitude;
    @Nullable
    private Double longitude;
}