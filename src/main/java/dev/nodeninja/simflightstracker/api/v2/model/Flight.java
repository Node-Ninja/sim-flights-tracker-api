package dev.nodeninja.simflightstracker.api.v2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
    private Integer id;
    private String pilotName;
    private String callsign;
    private Double latitude;
    private Double longitude;
    private Integer altitude;
    private Integer heading;
    private Integer groundSpeed;
    private String transponder;
    private FlightPlan flightPlan;
}
