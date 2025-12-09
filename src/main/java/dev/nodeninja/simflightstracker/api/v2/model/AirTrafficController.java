package dev.nodeninja.simflightstracker.api.v2.model;

import dev.nodeninja.simflightstracker.tracker.model.ivao.IvaoAtcPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

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
    private List<String> textAtis;
    private OffsetDateTime logonTime;
    private Integer rating;
}
