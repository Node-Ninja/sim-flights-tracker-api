package dev.nodeninja.simflightstracker.model.vatsim;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VatsimEventOrganiser {
    private String region;
    @Nullable
    private String division;
    @Nullable
    private String subdivision;
    @Nullable
    private Boolean organisedByVatsim;
}
