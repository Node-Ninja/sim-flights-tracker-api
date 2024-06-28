package dev.nodeninja.simflightstracker.tracker.model.ivao;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IvaoAirportSummary {
    private String icao;
    @Nullable
    private String iata;
    private String name;
    private String countryId;
    private String city;
    private Double latitude;
    private Double longitude;
    private Boolean military;
}
