package dev.nodeninja.simflightstracker.model.vatsim.response;

import dev.nodeninja.simflightstracker.model.AtcSummary;
import dev.nodeninja.simflightstracker.model.FlightSummary;
import dev.nodeninja.simflightstracker.model.dto.vatsim.VatsimDataApiResponse;
import dev.nodeninja.simflightstracker.model.vatsim.VatsimAtc;
import dev.nodeninja.simflightstracker.model.vatsim.VatsimFlight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VatsimLiveData {
    private List<FlightSummary> flights;
    private List<AtcSummary> controllers;

    public static VatsimLiveData mapVatsimLiveDataSummary(VatsimDataApiResponse apiResponse) {
        return VatsimLiveData.builder()
                .flights(apiResponse.getPilots().stream().map(VatsimFlight::toSummary).collect(Collectors.toList()))
                .controllers(apiResponse.getControllers().stream().map(VatsimAtc::toSummary).collect(Collectors.toList()))
                .build();
    }
}
