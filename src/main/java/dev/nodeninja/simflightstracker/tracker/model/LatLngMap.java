package dev.nodeninja.simflightstracker.tracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LatLngMap {
    private Double lat;
    private Double lng;
}
