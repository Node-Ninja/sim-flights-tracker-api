package dev.nodeninja.simflightstracker.model.dto.vatsim;

import dev.nodeninja.simflightstracker.model.vatsim.VatsimAtc;
import dev.nodeninja.simflightstracker.model.vatsim.VatsimAtis;
import dev.nodeninja.simflightstracker.model.vatsim.VatsimFlight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VatsimDataApiResponse {
    private List<VatsimFlight> pilots;
    private List<VatsimAtc> controllers;
    private List<VatsimAtis> atis;
}
