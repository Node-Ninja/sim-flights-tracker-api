package dev.nodeninja.simflightstracker.config;

import dev.nodeninja.simflightstracker.tracker.external.AuthenticationClient;
import dev.nodeninja.simflightstracker.tracker.external.IvaoClient;
import dev.nodeninja.simflightstracker.tracker.external.VatsimClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;
import java.util.List;

@Slf4j
@Configuration
@EnableRetry
@RequiredArgsConstructor
public class RestClientConfig {

    private final ApplicationConfigProperties configProps;

    @Bean
    public AuthenticationClient authenticationClient() {
        RestClient restClient = RestClient.builder()
                .requestFactory(getRequestFactory(Duration.ofSeconds(60)))
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
                })
                .build();

        final RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
        final HttpServiceProxyFactory proxy = HttpServiceProxyFactory.builderFor(restClientAdapter).build();

        return proxy.createClient(AuthenticationClient.class);
    }

    @Bean
    public VatsimClient vatsimClient() {
        RestClient restClient = RestClient.builder()
                .requestFactory(getRequestFactory(Duration.ofSeconds(60)))
                .baseUrl(configProps.getVatsim().getHost().getV3())
                .build();

        final RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
        final HttpServiceProxyFactory proxy = HttpServiceProxyFactory.builderFor(restClientAdapter).build();

        return proxy.createClient(VatsimClient.class);

    }

    @Bean
    public IvaoClient ivaoClient() {
        RestClient restClient = RestClient.builder()
                .requestFactory(getRequestFactory(Duration.ofSeconds(60)))
                .baseUrl(configProps.getIvao().getHost().getV2())
                .build();

        final RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
        final HttpServiceProxyFactory proxy = HttpServiceProxyFactory.builderFor(restClientAdapter).build();

        return proxy.createClient(IvaoClient.class);
    }

    private ClientHttpRequestFactory getRequestFactory(final Duration timeout) {
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setConnectTimeout(Timeout.ofSeconds(timeout.getSeconds()))
                .build();

        //  Socket Timeout;
        SocketConfig socketConfig = SocketConfig.custom()
                .setSoTimeout(Timeout.ofSeconds(timeout.getSeconds()))
                .build();

        //  Request timeout;
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofSeconds(timeout.getSeconds()))
                .build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setDefaultSocketConfig(socketConfig);
        connectionManager.setDefaultConnectionConfig(connectionConfig);

        HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectionRequestTimeout(timeout);
        requestFactory.setConnectTimeout(timeout);
        requestFactory.setHttpClient(httpClient);

        return requestFactory;
    }
}
