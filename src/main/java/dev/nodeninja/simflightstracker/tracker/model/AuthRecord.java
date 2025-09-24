package dev.nodeninja.simflightstracker.tracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "auth")
public class AuthRecord {
    @Id
    private String authId;
    private String simNetwork;
    private String type;
    private String token;
    private String refresh;
    private Integer expiresIn;
    private Instant expiryDate;
    private Instant refreshExpiryDate;
    private VatsimUserDetails vatsimUserDetails;
}
