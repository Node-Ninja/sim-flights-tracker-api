package dev.nodeninja.simflightstracker.model.ivao;

import dev.nodeninja.simflightstracker.model.LatLngMap;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IvaoAtcPosition {
    private String airportId;
    private String atcCallsign;
    private Boolean military;
    @Nullable
    private String middleIdentifier;
    private String position;
    private String composePosition;
    private List<LatLngMap> regionMap;
    private List<List<Double>> regionMapPolygon;
    private IvaoAirportSummary airport;
}
