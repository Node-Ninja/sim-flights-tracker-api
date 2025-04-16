package dev.nodeninja.simflightstracker.api.v2.model;

import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class IvaoEvent {
    private Integer id;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private String title;
    private String description;
    private String imageUrl;
    private String infoUrl;
    private String eventType;
    private Boolean hqeAward;
    private List<String> divisions;
    private List<String> airports;
    @Null
    private List<EventRoute> routes;

    @Data
    @AllArgsConstructor
    @Builder
    private static class EventRoute {
        private String departureIcao;
        private String arrivalIcao;
        private String route;
    }
}
