package dev.nodeninja.simflightstracker.model.vatsim;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VatsimTransceiver {
    private String callsign;
    private List<TransceiverItem> transceivers;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class TransceiverItem {
        private Double heightMslM;
        private Double heightAglM;
        private Integer frequency;
        private Double lonDeg;
        private Double latDeg;
        private Integer id;
    }
}
