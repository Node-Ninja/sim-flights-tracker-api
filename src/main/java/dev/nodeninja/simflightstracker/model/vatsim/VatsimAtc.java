package dev.nodeninja.simflightstracker.model.vatsim;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dev.nodeninja.simflightstracker.model.AirTrafficController;
import dev.nodeninja.simflightstracker.model.AtcSummary;
import dev.nodeninja.simflightstracker.util.GenericUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VatsimAtc {
    private Integer cid;
    private String name;
    private String callsign;
    private String frequency;
    private Integer facility;
    private Integer rating;
    private String server;
    private Integer visualRange;
    @Nullable
    private String[] textAtis;
    private OffsetDateTime lastUpdated;
    private OffsetDateTime logonTime;

    public AtcSummary toSummary() {
        return AtcSummary.builder()
                .callsign(this.callsign)
                .frequency(this.frequency)
                .facility(GenericUtils.mapControllerType(this.callsign))
                .build();
    }

    public AirTrafficController toGenericController() {
        return AirTrafficController.builder()
                .id(this.cid)
                .name(this.name)
                .frequency(this.frequency)
                .facility(this.facility)
                .callsign(this.callsign)
                .connectionType(null)
                .atcPosition(null)
                .build();
    }
}
