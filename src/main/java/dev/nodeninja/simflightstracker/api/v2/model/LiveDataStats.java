package dev.nodeninja.simflightstracker.api.v2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveDataStats {
    private int totalVatsimFlights;
    private int totalVatsimControllers;
    private int totalIvaoFlights;
    private int totalIvaoControllers;
}
