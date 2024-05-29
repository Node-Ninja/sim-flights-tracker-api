package dev.nodeninja.simflightstracker.model.vatsim;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dev.nodeninja.simflightstracker.model.EventSummary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VatsimEvent {
    private Integer id;
    private String type;
    private String name;
    private String link;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private String shortDescription;
    private String description;
    private String banner;
    private List<VatsimEventOrganiser> organisers;
    private List<VatsimEventRoute> routes;
    private List<VatsimEventAirport> airports;

    public EventSummary toSummary() {
        return EventSummary.builder()
                .id(this.id)
                .name(this.name)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .build();
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class VatsimEventAirport {
        private String icao;
    }
}
