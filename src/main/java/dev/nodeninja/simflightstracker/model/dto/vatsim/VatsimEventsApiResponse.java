package dev.nodeninja.simflightstracker.model.dto.vatsim;

import dev.nodeninja.simflightstracker.model.vatsim.VatsimEvent;
import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class VatsimEventsApiResponse extends VatsimV3BaseResponse<List<VatsimEvent>> {
}
