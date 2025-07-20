package dev.nodeninja.simflightstracker.api.v2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthedUserDetails {
    private String cid;
    private String fullName;
    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private String countryCode;
    private String controllerRating;
    private String pilotRating;
    private String division;
}
