package dev.nodeninja.simflightstracker.api.v2.model;

import dev.nodeninja.simflightstracker.tracker.model.ivao.IvaoAtcPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirTrafficController {
    private Integer id;
    private String name;
    private String frequency;
    private Integer facility;
    private String callsign;
    private String connectionType;
    private IvaoAtcPosition atcPosition;
}
