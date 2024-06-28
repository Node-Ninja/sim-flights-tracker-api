package dev.nodeninja.simflightstracker.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;



@Configuration
@RequiredArgsConstructor
public class WebClientConfig {
    private final ApplicationConfigProperties configProps;

    @Bean
    WebClient webClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .codecs(codecs -> codecs
                        .defaultCodecs()
                        .maxInMemorySize(2000 * 1024))
                .build();
    }
}
