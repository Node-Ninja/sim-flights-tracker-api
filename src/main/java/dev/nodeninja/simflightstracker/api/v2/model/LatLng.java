package dev.nodeninja.simflightstracker.api.v2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class LatLng {
    private double latitude;
    private double longitude;
    private int altitude;
    private long timestamp;
    private int speed;
}
