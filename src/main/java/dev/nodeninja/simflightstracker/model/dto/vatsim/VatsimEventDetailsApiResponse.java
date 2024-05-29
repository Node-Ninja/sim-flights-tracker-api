package dev.nodeninja.simflightstracker.model.dto.vatsim;

import dev.nodeninja.simflightstracker.model.vatsim.VatsimEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VatsimEventDetailsApiResponse extends VatsimV3BaseResponse<VatsimEvent> {
}
