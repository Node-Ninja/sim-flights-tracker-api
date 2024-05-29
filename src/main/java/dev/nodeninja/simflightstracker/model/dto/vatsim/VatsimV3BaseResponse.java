package dev.nodeninja.simflightstracker.model.dto.vatsim;

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
