package dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class VatsimV3BaseResponse <T> {
    private T data;
}
