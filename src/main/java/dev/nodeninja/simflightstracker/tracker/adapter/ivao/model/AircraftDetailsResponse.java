package dev.nodeninja.simflightstracker.tracker.adapter.ivao.model;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AircraftDetailsResponse {
    private String icaoCode;
    @Nullable
    private String iataCode;
    private String description;
    private String model;
    private String wakeTurbulence;
    private Integer manufactureId;
    private Integer numberEngines;
    private Boolean isMilitary;
    private CraftManufacturer manufacture;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CraftManufacturer {
        private Integer id;
        private String name;
    }
}
