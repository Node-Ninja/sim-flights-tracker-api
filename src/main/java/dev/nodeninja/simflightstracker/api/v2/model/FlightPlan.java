package dev.nodeninja.simflightstracker.api.v2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightPlan {
    private String departure;
    private String arrival;
    private String alternate;
    private String altitude;
    private String aircraftFaa;
    private String aircraftShort;
    private String route;
}