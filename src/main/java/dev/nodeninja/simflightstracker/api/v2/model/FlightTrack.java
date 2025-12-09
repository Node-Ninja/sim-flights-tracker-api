package dev.nodeninja.simflightstracker.api.v2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightTrack {
    @Id
    @JsonIgnore
    private ObjectId id;
    @JsonIgnore
    private String callsign;
    private double latitude;
    private double longitude;
    private int altitude;
    private long timestamp;
    private int speed;
}

