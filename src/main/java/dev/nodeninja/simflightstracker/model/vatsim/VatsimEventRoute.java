package dev.nodeninja.simflightstracker.model.vatsim;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VatsimEventRoute {
    private String departure;
    private String arrival;
    private String route;
}
