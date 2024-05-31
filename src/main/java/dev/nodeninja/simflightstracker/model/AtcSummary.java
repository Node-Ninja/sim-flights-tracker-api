package dev.nodeninja.simflightstracker.model;

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
    private  ControllerType facility;
}