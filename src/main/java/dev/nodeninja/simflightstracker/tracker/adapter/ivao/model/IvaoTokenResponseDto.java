package dev.nodeninja.simflightstracker.tracker.adapter.ivao.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class IvaoTokenResponseDto {
    private String accessToken;
    private String tokenType;
    private Integer expiresIn;
}
