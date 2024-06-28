package dev.nodeninja.simflightstracker.tracker.adapter.ivao.model;

import dev.nodeninja.simflightstracker.api.v2.model.AtcSummary;
import dev.nodeninja.simflightstracker.api.v2.model.AirTrafficController;
import dev.nodeninja.simflightstracker.tracker.model.ivao.IvaoAtcPosition;
import dev.nodeninja.simflightstracker.tracker.model.ivao.IvaoAtcSession;
import dev.nodeninja.simflightstracker.tracker.model.ivao.IvaoSoftwareType;
import dev.nodeninja.simflightstracker.util.GenericUtils;
import jakarta.annotation.Nullable;
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
    @Nullable
    private IvaoAtcPosition atcPosition;
}
