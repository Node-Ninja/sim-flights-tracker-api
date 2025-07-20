package dev.nodeninja.simflightstracker.tracker.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VatsimTokenResponse {
    private List<String> scopes;
    private String tokenType;
    private Integer expiresIn;
    private String accessToken;
    private String refreshToken;
}
