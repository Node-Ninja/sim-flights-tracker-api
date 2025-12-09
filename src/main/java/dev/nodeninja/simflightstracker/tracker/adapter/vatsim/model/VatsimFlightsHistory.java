package dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model;

import dev.nodeninja.simflightstracker.api.v2.model.VatsimFlightHistoryItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class VatsimFlightsHistory {
    private Integer count;
    private List<VatsimFlightHistoryItem> items;
}
