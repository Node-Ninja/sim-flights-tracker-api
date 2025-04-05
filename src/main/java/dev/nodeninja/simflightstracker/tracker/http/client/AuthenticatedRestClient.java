package dev.nodeninja.simflightstracker.tracker.http.client;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.nodeninja.simflightstracker.config.ApplicationConfigProperties;
import dev.nodeninja.simflightstracker.exceptions.AuthenticationException;
import dev.nodeninja.simflightstracker.tracker.external.AuthenticationClient;
import dev.nodeninja.simflightstracker.tracker.external.model.OAuthCredentials;
import dev.nodeninja.simflightstracker.tracker.external.model.OAuthData;
import dev.nodeninja.simflightstracker.tracker.http.model.IDProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticatedRestClient {
    private String ivaoToken = null;
    private String vatsimToken = null;
    private String navigraphToken = null;

    private final ApplicationConfigProperties configProps;
    private final AuthenticationClient authenticationClient;

    @Retryable(
            label = "requestWithRetry",
            recover = "requestWithRetryRecover",
            retryFor = {AuthenticationException.class, HttpClientErrorException.class},
            noRetryFor = {HttpClientErrorException.BadRequest.class},
            backoff = @Backoff(maxDelayExpression = "PT5S")
    )
    public <T> T requestWithRetry(IDProvider provider, final Function<HttpHeaders, T> function) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getToken(provider));

        return function.apply(headers);
    }

    @Retryable(
            label = "getToken",
            retryFor = Exception.class,
            backoff = @Backoff(maxDelayExpression = "PT5S")
    )
    private String getToken(IDProvider provider) {

        var shouldRefresh = switch (provider) {
            case IVAO -> hasTokenExpired(ivaoToken);
            case VATSIM -> hasTokenExpired(vatsimToken);
            case NAVIGRAPH -> hasTokenExpired(navigraphToken);
        };

        if (shouldRefresh || ivaoToken == null) {
            log.info("Getting new authentication token");

            try {
                OAuthCredentials credentials = getOAuthCredentials(provider);

                OAuthData authData = authenticationClient.getToken(
                        credentials.getTokenEndpoint(),
                        credentials.getClientId(),
                        credentials.getClientSecret(),
                        credentials.getGrantType(),
                        credentials.getScope(),
                        credentials.getUsername(),
                        credentials.getPassword()
                );

                if (StringUtils.isNotBlank(authData.getAccessToken())) {
                    setToken(authData.getAccessToken(), provider);

                    return authData.getAccessToken();
                } else {
                    throw new AuthenticationException("Authentication failed");
                }
            } catch (Exception e) {
                log.error("Error fetching authentication token", e);
                throw new AuthenticationException("Authentication failed");
            }
        } else {
            return switch (provider) {
                case IVAO -> ivaoToken;
                case VATSIM -> vatsimToken;
                case NAVIGRAPH -> navigraphToken;
            };
        }
    }

    private void  setToken(String token, IDProvider provider) {
        switch (provider) {
            case IVAO: ivaoToken = token;
            case VATSIM: vatsimToken = token;
            case NAVIGRAPH: navigraphToken = token;
        }
    }

    private Boolean hasTokenExpired(String token) {
        if (StringUtils.isNotBlank(token)) {
            DecodedJWT jwt = JWT.decode(token);

            return jwt.getExpiresAt().before(new Date(System.currentTimeMillis() - 300000));
        }

        return true;
    }

    private OAuthCredentials getOAuthCredentials(IDProvider provider) {
        ApplicationConfigProperties.IvaoConfigProperties.IvaoOAuthProperties ivaoOauth = configProps.getIvao().getOauth();
        ApplicationConfigProperties.VatsimConfigProperties.VatsimOAuthProperties vatsimOauth = configProps.getVatsim().getOAuth();


        if (provider == IDProvider.IVAO) {
            return OAuthCredentials.builder()
                    .clientId(ivaoOauth.getClientId())
                    .clientSecret(ivaoOauth.getClientSecret())
                    .username("")
                    .password("")
                    .grantType(ivaoOauth.getGrantType())
                    .scope(ivaoOauth.getScope())
                    .tokenEndpoint(URI.create(ivaoOauth.getTokenUri()))
                    .build();
        } else {
            //  vatsim
            return OAuthCredentials.builder()
                    .clientId(vatsimOauth.getClientId())
                    .clientSecret(vatsimOauth.getClientSecret())
                    .username("")
                    .password("")
                    .grantType(vatsimOauth.getGrantType())
                    .scope(vatsimOauth.getScope())
                    .tokenEndpoint(URI.create(vatsimOauth.getTokenUri()))
                    .build();
        }
    }
}
