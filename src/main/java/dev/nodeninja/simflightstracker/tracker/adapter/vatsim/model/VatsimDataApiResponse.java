package dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VatsimDataApiResponse {
    private List<VatsimFlight> pilots;
    private List<VatsimAtc> controllers;
    private List<VatsimAtis> atis;
}
