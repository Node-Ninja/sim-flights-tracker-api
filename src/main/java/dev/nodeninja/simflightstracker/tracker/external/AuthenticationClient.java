package dev.nodeninja.simflightstracker.tracker.external;

import dev.nodeninja.simflightstracker.tracker.external.model.OAuthData;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.service.annotation.PostExchange;

import java.net.URI;

public interface AuthenticationClient {
    @PostExchange
    OAuthData getToken(
            URI uri,
            @RequestPart("client_id") String clientId,
            @RequestPart("client_secret") String clientSecret,
            @RequestPart("grant_type") String grantType,
            @RequestPart("scope") String scope,
            @RequestPart("username") String username,
            @RequestPart("password") String password
    );
}
