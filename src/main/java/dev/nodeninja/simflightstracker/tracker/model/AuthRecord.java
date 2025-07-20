package dev.nodeninja.simflightstracker.tracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private VatsimUserDetails vatsimUserDetails;
}
