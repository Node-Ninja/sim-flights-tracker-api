package dev.nodeninja.simflightstracker.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("sft")
public class ApplicationConfigProperties {
    private VatsimConfigProperties vatsim = new VatsimConfigProperties();
    private IvaoConfigProperties ivao = new IvaoConfigProperties();

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
}
