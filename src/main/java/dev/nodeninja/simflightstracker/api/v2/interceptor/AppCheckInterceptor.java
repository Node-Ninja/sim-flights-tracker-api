package dev.nodeninja.simflightstracker.api.v2.interceptor;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.nodeninja.simflightstracker.config.ApplicationConfigProperties;
import dev.nodeninja.simflightstracker.exceptions.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.URI;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppCheckInterceptor implements HandlerInterceptor {

    private final ApplicationConfigProperties configProps;

    @SuppressWarnings("null")
    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {

        String appCheckToken = request.getHeader("Authorization");
        var firebaseProjectName = configProps.getFirebase().getProjectName();
        var firebaseProjectNumber = configProps.getFirebase().getProjectNumber();
        var firebaseAppCheckKeyUrl = configProps.getFirebase().getAppCheckJwk();

        if (appCheckToken == null || !appCheckToken.startsWith("Bearer ") || appCheckToken.length() < 36) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        try {
            String token = appCheckToken.substring(7);

            if (StringUtils.isBlank(token)) {
                throw new AuthenticationException("Invalid JWT token");
            }

            DecodedJWT tk = JWT.decode(token);
            List<String> audience = tk.getAudience();
            Date expiry = tk.getExpiresAt();

            if (
                    expiry.before(new Date()) ||
                    !audience.contains(firebaseProjectName) ||
                    !audience.contains(firebaseProjectNumber)
            ) {
                throw new AuthenticationException("Invalid JWT token");
            }

            //  verify if the token was signed by Firebase;
            JwkProvider provider = new JwkProviderBuilder(URI.create(firebaseAppCheckKeyUrl).toURL())
                    .cached(5, Duration.ofHours(12))
                    .build();

            Jwk jwk = provider.get(tk.getKeyId());
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);

            algorithm.verify(tk);

            return true;
        } catch (SignatureVerificationException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}