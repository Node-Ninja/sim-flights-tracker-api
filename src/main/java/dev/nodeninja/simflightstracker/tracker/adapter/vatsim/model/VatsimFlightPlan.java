package dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VatsimFlightPlan {
    @JsonProperty("flight_rules")
    private String flightRules;
    private String aircraft;
    @JsonProperty("aircraft_faa")
    private String aircraftFaa;
    @JsonProperty("aircraft_short")
    private String aircraftShort;
    private String departure;
    private String arrival;
    private String alternate;
    @JsonProperty("cruise_tas")
    private String cruiseTas;
    private String altitude;
    private String deptime;
    @JsonProperty("enroute_time")
    private String enrouteTime;
    @JsonProperty("fuel_time")
    private String fuelTime;
    private String remarks;
    private String route;
    @JsonProperty("revision_id")
    private Integer revisionId;
    @JsonProperty("assigned_transponder")
    private String assignedTransponder;
}
