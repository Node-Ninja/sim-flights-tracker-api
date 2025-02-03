package dev.nodeninja.simflightstracker.api.v2.interceptor;

import dev.nodeninja.simflightstracker.config.ApplicationConfigProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppCheckInterceptor implements HandlerInterceptor {

    private final ApplicationConfigProperties configProps;

    @SuppressWarnings("null")
    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        return true;
//        String appCheckToken = request.getHeader("Authorization");
//        var firebaseProjectName = configProps.getFirebase().getProjectName();
//        var firebaseProjectNumber = configProps.getFirebase().getProjectNumber();
//        var firebaseAppCheckKeyUrl = configProps.getFirebase().getAppCheckJwk();
//
//        if (appCheckToken == null || !appCheckToken.startsWith("Bearer ") || appCheckToken.length() < 36) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return false;
//        }
//
//        try {
//            String token = appCheckToken.substring(7);
//
//            if (StringUtils.isBlank(token)) {
//                throw new AuthenticationException("Invalid JWT token");
//            }
//
//            DecodedJWT tk = JWT.decode(token);
//            List<String> audience = tk.getAudience();
//            Date expiry = tk.getExpiresAt();
//
//            if (
//                    expiry.before(new Date()) ||
//                    !audience.contains(firebaseProjectName) ||
//                    !audience.contains(firebaseProjectNumber)
//            ) {
//                throw new AuthenticationException("Invalid JWT token");
//            }
//
//            //  verify if the token was signed by Firebase;
//            JwkProvider provider = new JwkProviderBuilder(new URL(firebaseAppCheckKeyUrl))
//                    .cached(5, Duration.ofHours(12))
//                    .build();
//
//            Jwk jwk = provider.get(tk.getKeyId());
//            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
//
//            algorithm.verify(tk);
//
//            return true;
//        } catch (SignatureVerificationException e) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return false;
//        } catch (Exception e) {
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            return false;
//        }
    }
}
