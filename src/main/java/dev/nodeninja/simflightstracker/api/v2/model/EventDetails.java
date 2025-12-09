package dev.nodeninja.simflightstracker.api.v2.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EventDetails {
    private Integer id;
    private String type;
    private String name;
    private String link;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private String shortDescription;
    private String description;
    private String banner;
    private List<EventOrganiser> organisers;
    private List<EventRoute> routes;
    private List<EventAirport> airports;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventAirport {
        private String icao;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventOrganiser {
        private String region;
        @Nullable
        private String division;
        @Nullable
        private String subdivision;
        @Nullable
        private Boolean organisedByVatsim;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventRoute {
        private String departure;
        private String arrival;
        private String route;
    }
}
