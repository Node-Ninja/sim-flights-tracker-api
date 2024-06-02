package dev.nodeninja.simflightstracker.model.ivao;

import dev.nodeninja.simflightstracker.model.Flight;
import dev.nodeninja.simflightstracker.model.FlightPlan;
import dev.nodeninja.simflightstracker.model.FlightPlanSummary;
import dev.nodeninja.simflightstracker.model.FlightSummary;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IvaoFlight {
    private Integer id;
    private String callsign;
    private String connectorType;
    private String serverId;
    private OffsetDateTime createdAt;
    private Integer time;
    private IvaoPilotSession pilotSession;
    private IvaoSoftwareType softwareType;
    private IvaoLastTrack lastTrack;
    @Nullable
    private IvaoFlightPlan flightPlan;

    public FlightSummary toSummary() {
        var hasFlightPlan = this.flightPlan != null;
        var hasDepartureArrival = hasFlightPlan && this.flightPlan.arrival != null && this.flightPlan.departure != null;
        var hasLastTrack = this.lastTrack != null;

        var flightPlanSummary = hasFlightPlan && hasDepartureArrival
                ? FlightPlanSummary.builder()
                    .arrival(this.flightPlan.arrival.icao)
                    .departure(this.flightPlan.departure.icao)
                    .build()
                : null;

        return FlightSummary.builder()
                .callsign(this.callsign)
                .latitude(hasLastTrack ? this.lastTrack.latitude : 0.0)
                .longitude(hasLastTrack ? this.lastTrack.longitude : 0.0)
                .altitude(hasLastTrack ? this.lastTrack.altitude : 0)
                .groundspeed(hasLastTrack ? this.lastTrack.groundSpeed : 0)
                .heading(hasLastTrack ? this.lastTrack.heading : 0)
                .flightPlan(flightPlanSummary)
                .build();
    }

    public Flight toGenericFlight() {
        var flightPlan = (this.flightPlan != null && this.flightPlan.departure != null)
                ? FlightPlan.builder()
                .departure(this.flightPlan.getDepartureId())
                    .arrival(this.flightPlan.getArrivalId())
                    .alternate("")
                    .altitude(this.lastTrack.altitude.toString())
                    .aircraftFaa("")
                    .aircraftShort(this.flightPlan.getAircraftId())
                    .route("")
                    .build()
                : null;

        return Flight.builder()
                .id(this.id)
                .pilotName("")
                .callsign(this.callsign)
                .latitude(this.lastTrack.latitude)
                .longitude(this.lastTrack.longitude)
                .altitude(this.lastTrack.altitude)
                .heading(this.lastTrack.heading)
                .groundSpeed(this.lastTrack.groundSpeed)
                .transponder(this.lastTrack.transponder.toString())
                .flightPlan(flightPlan)
                .build();
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class IvaoPilotSession {
        private String simulatorId;
        private Integer textureId;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class IvaoSoftwareType {
        private String id;
        private String name;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class IvaoLastTrack {
        private Integer altitude;
        private Integer altitudeDifference;
        private Double arrivalDistance;
        private Double departureDistance;
        private Integer groundSpeed;
        private Integer heading;
        private Double latitude;
        private Double longitude;
        private Boolean onGround;
        private String state;
        private String timestamp;
        private Integer transponder;
        private String transponderMode;
        private Integer time;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class IvaoAircraft {
        private String icaoCode;
        private String model;
        private String wakeTurbulence;
        private Boolean isMilitary;
        private String description;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class IvaoFlightPlan {
        private Integer id;
        private String aircraftId;
        private String departureId;
        private String arrivalId;
        private IvaoAircraft aircraft;
        private IvaoFlightPlanAirport departure;
        private IvaoFlightPlanAirport arrival;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class IvaoFlightPlanAirport {
        private String icao;
        private String iata;
        private String name;
        private String countryId;
        private String city;
        private Double latitude;
        private Double longitude;
        private Boolean military;
    }
}
