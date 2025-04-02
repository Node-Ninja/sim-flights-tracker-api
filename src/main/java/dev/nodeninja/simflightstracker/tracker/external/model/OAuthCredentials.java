package dev.nodeninja.simflightstracker.tracker.external.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.net.URI;

@Data
@AllArgsConstructor
@Builder
public class OAuthCredentials {
    private String clientId;
    private String clientSecret;
    private String scope;
    private String grantType;
    private String username;
    private String password;
    private URI tokenEndpoint;
}
