package dev.nodeninja.simflightstracker.model.vatsim;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class VatsimAtis {
    private long cid;
    private String name;
    @JsonProperty("callsign")
    private String callSign;
    private String frequency;
    private Integer facility;
    private Integer rating;
    private String server;
    @JsonProperty("visual_range")
    private Integer visualRange;
    @JsonProperty("atis_code")
    private String atisCode;
    @JsonProperty("text_atis")
    private List<String> textAtis;
    @JsonProperty("last_updated")
    private OffsetDateTime lastUpdated;
    @JsonProperty("logon_time")
    private OffsetDateTime logonTime;
}
