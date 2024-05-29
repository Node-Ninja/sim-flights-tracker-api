package dev.nodeninja.simflightstracker.model.vatsim;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dev.nodeninja.simflightstracker.model.Flight;
import dev.nodeninja.simflightstracker.model.FlightPlan;
import dev.nodeninja.simflightstracker.model.FlightPlanSummary;
import dev.nodeninja.simflightstracker.model.FlightSummary;
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

    public FlightSummary toSummary() {
        var flightPlanSummary = this.flightPlan != null
                ? FlightPlanSummary.builder()
                    .departure(this.flightPlan.getDeparture())
                    .arrival(this.flightPlan.getArrival())
                    .build()
                : null;

        return FlightSummary.builder()
                .callsign(this.callsign)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .flightPlan(flightPlanSummary)
                .build();
    }

    public Flight toGenericFlight() {
        var flightPlan = this.flightPlan != null
                ? FlightPlan.builder()
                    .departure(this.flightPlan.getDeparture())
                    .arrival(this.flightPlan.getArrival())
                    .alternate(this.flightPlan.getAlternate())
                    .altitude(this.flightPlan.getAltitude())
                    .aircraftFaa(this.flightPlan.getAircraftFaa())
                    .aircraftShort(this.flightPlan.getAircraftShort())
                    .route(this.flightPlan.getRoute())
                    .build()
                : null;

        return Flight.builder()
                .id(this.cid)
                .pilotName(this.name)
                .callsign(this.callsign)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .altitude(this.altitude)
                .heading(this.heading)
                .groundSpeed(this.groundspeed)
                .transponder(this.transponder)
                .flightPlan(flightPlan)
                .build();
    }
}
