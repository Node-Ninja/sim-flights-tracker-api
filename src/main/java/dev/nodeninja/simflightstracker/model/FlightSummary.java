package dev.nodeninja.simflightstracker.model;

import dev.nodeninja.simflightstracker.model.vatsim.VatsimFlight;
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
    @Nullable
    private FlightPlanSummary flightPlan;
}