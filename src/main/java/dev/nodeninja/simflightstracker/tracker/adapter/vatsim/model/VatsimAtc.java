package dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.annotation.Nullable;
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
public class VatsimAtc {
    private Integer cid;
    private String name;
    private String callsign;
    private String frequency;
    private Integer facility;
    private Integer rating;
    private String server;
    private Integer visualRange;
    @Nullable
    private List<String> textAtis;
    private OffsetDateTime lastUpdated;
    private OffsetDateTime logonTime;
}
