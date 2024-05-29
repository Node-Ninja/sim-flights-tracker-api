package dev.nodeninja.simflightstracker.model.ivao.response;

import dev.nodeninja.simflightstracker.model.AtcSummary;
import dev.nodeninja.simflightstracker.model.FlightSummary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IvaoLiveData {
    private List<FlightSummary> flights;
    private List<AtcSummary> controllers;
}
