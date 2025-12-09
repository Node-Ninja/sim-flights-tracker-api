package dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VatsimFlight {
    private Integer cid;
    private String name;
    private String callsign;
    private String server;
    private Integer pilotRating;
    private String militaryRating;
    private Double latitude;
    private Double longitude;
    private Integer altitude;
    private Integer groundspeed;
    private String transponder;
    private Integer heading;
    private Double qnhIHg;
    private Double qnhMb;
    @Nullable
    private VatsimFlightPlan flightPlan;
    private String logonTime;
    private String lastUpdated;
}
