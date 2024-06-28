package dev.nodeninja.simflightstracker.api.v2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightSummary {
    private String callsign;
    private Double latitude;
    private Double longitude;
    private Integer groundspeed;
    private Integer heading;
    private Integer altitude;
    @Nullable
    private FlightPlanSummary flightPlan;
}