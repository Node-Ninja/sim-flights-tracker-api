package dev.nodeninja.simflightstracker.model.ivao;

import dev.nodeninja.simflightstracker.model.AirTrafficController;
import dev.nodeninja.simflightstracker.model.AtcSummary;
import dev.nodeninja.simflightstracker.util.GenericUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IvaoAtc {
    private Integer id;
    private Integer userId;
    private String callsign;
    private String serverId;
    private String connectionType;
    private OffsetDateTime createdAt;
    private Integer time;
    private IvaoAtcSession atcSession;
    private IvaoSoftwareType softwareType;
    private IvaoAtcPosition atcPosition;

    public AtcSummary toSummary() {
        return AtcSummary.builder()
                .callsign(this.callsign)
                .frequency(this.atcSession.getFrequency().toString())
                .facility(GenericUtils.mapControllerType(this.callsign))
                .build();
    }

    public AirTrafficController toGenericController() {
        return AirTrafficController.builder()
                .id(this.id)
                .name("")
                .frequency(this.atcSession.getFrequency().toString())
                .facility(100)
                .callsign(this.callsign)
                .connectionType(this.connectionType)
                .atcPosition(this.atcPosition)
                .build();
    }
}
