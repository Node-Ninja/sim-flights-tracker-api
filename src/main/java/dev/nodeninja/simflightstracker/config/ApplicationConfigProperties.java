package dev.nodeninja.simflightstracker.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("sft")
public class ApplicationConfigProperties {
    private final VatsimConfigProperties vatsim = new VatsimConfigProperties();
    private final IvaoConfigProperties ivao = new IvaoConfigProperties();
    private final FirebaseProperties firebase = new FirebaseProperties();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VatsimConfigProperties {
        private VatsimHostProperties host = new VatsimHostProperties();

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class VatsimHostProperties {
            private String v2;
            private String v3;
            private String metar;
            private String core;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IvaoConfigProperties {
        private IvaoOAuthProperties oauth = new IvaoOAuthProperties();
        private IvaoHostProperties host = new IvaoHostProperties();

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class IvaoOAuthProperties {
            private String clientId;
            private String clientSecret;
            private String grantType;
            private String scope;
            private String tokenUri;
        }

        @Data

        @AllArgsConstructor
        @NoArgsConstructor
        public static class IvaoHostProperties {
            private String v2;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FirebaseProperties {
        private String projectName;
        private String projectNumber;
        private String appCheckJwk;
    }
}
