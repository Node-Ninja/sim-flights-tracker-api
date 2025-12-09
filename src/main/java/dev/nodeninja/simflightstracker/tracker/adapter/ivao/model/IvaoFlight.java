package dev.nodeninja.simflightstracker.tracker.adapter.ivao.model;

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

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IvaoPilotSession {
        private String simulatorId;
        private Integer textureId;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IvaoSoftwareType {
        private String id;
        private String name;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IvaoLastTrack {
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
    public static class IvaoAircraft {
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
    public static class IvaoFlightPlan {
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
    public static class IvaoFlightPlanAirport {
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
