package dev.nodeninja.simflightstracker.api.v2.model;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Aircraft {
    private String icaoCode;
    @Nullable
    private String iataCode;
    private String description;
    private String model;
    private String wakeTurbulence;
    private Integer numberEngines;
    private Boolean isMilitary;
    private String manufacture;
}
