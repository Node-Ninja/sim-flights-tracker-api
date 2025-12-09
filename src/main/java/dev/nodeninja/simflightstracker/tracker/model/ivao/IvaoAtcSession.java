package dev.nodeninja.simflightstracker.tracker.model.ivao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IvaoAtcSession {
    private Double frequency;
    private String position;
}
