package dev.nodeninja.simflightstracker.tracker.model.ivao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IvaoSoftwareType {
    private String id;
    private String name;
}
