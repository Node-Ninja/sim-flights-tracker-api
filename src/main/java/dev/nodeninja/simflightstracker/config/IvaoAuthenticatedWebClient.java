package dev.nodeninja.simflightstracker.config;

import dev.nodeninja.simflightstracker.exceptions.AuthenticationException;
import dev.nodeninja.simflightstracker.exceptions.BusinessException;
import dev.nodeninja.simflightstracker.model.dto.ivao.response.IvaoTokenResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Slf4j
@Component
@RequiredArgsConstructor
public class IvaoAuthenticatedWebClient {
    private static String token;

    private final WebClient webClient;
    private final ApplicationConfigProperties configProps;

    public <RequestType, ResponseType> Mono<ResponseType> sendRequest(
            final String uri,
            final HttpMethod httpMethod,
            final HttpHeaders headers,
            final Mono<RequestType> requestData,
            final Class<RequestType> requestType,
            final Class<ResponseType> responseType
    ) {
        return request(uri, httpMethod, headers, requestData, requestType, ParameterizedTypeReference.forType(responseType));
    }

    public <RequestType, ResponseType> Mono<ResponseType> sendRequest(
            final String uri,
            final HttpMethod httpMethod,
            final HttpHeaders headers,
            final Mono<RequestType> requestData,
            final Class<RequestType> requestType,
            final ParameterizedTypeReference<ResponseType> responseType
    ) {
        return request(uri, httpMethod, headers, requestData, requestType, responseType);
    }

    private  <RequestType, ResponseType> Mono<ResponseType> request(
            final String uri,
            final HttpMethod httpMethod,
            final HttpHeaders headers,
            final Mono<RequestType> requestData,
            final Class<RequestType> requestType,
            final ParameterizedTypeReference<ResponseType> responseType
    ) {
        return getToken(false)
                .flatMap(ivaoToken -> {
                   var client = webClient
                           .method(httpMethod)
                           .uri(uri)
                           .contentType(MediaType.APPLICATION_JSON)
                           .accept(MediaType.APPLICATION_JSON)
                           .headers(getRequestHeaders(ivaoToken, headers));

                   //   if method is not get, then include body;
                   if (httpMethod == HttpMethod.POST || httpMethod == HttpMethod.PUT || httpMethod == HttpMethod.PATCH) {
                       client.body(requestData, requestType);
                   }

                   return client
                           .retrieve()
                           .onStatus(Predicate.isEqual(HttpStatus.UNAUTHORIZED), this::onUnAuthorised)
                           .bodyToMono(responseType)
                           .subscribeOn(Schedulers.immediate())
                           .retryWhen(Retry
                                   .backoff(
                                           3,
                                           Duration.ofSeconds(60)
                                   )
                                   .jitter(0.25)
                                   .filter(throwable -> throwable instanceof AuthenticationException)
                                   .doBeforeRetry(retrySignal -> log.warn("[execute] :: Retry number {} to {}", retrySignal.totalRetries() + 1, uri))
                                   .onRetryExhaustedThrow(((retryBackoffSpec, retrySignal) -> new AuthenticationException(
                                           String.format("[execute] :: Authentication request failed after %d retries", retrySignal.totalRetries())
                                   ))));
                });
    }

    protected Mono<String> getToken(final boolean shouldGetNewToken) {
        log.info("[getToken] :: shouldGetNewToken={}", shouldGetNewToken);

        if (shouldGetNewToken || StringUtils.isBlank(token)) {
            log.info("[getToken] :: Getting new IVAO token");

            MultiValueMap<String, String> oauthData = getOAuthData();

            return webClient
                    .post()
                    .uri(configProps.getIvao().getOauth().getTokenUri())
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(Mono.just(oauthData), MultiValueMap.class)
                    .retrieve()
                    .bodyToMono(IvaoTokenResponseDto.class)
                    .doOnError(error -> {
                        log.warn("[getToken] :: Error getting new IVAO token", error);
                        throw new BusinessException("Could not authenticate IVAO", "AUTH_FAILED", 412);
                    })
                    .handle((body, sink) -> {
                      if (StringUtils.isNotBlank(body.getAccessToken())) {
                          token = body.getAccessToken();
                          sink.next(token);
                      } else {
                          sink.error(new BusinessException("Could not authenticate IVAO", "AUTH_FAILED", 412));
                      }
                    });
        }

        return Mono.just(token);
    }

    protected MultiValueMap<String, String> getOAuthData() {
        MultiValueMap<String, String> oauthData = new LinkedMultiValueMap<>();
        oauthData.add("grant_type", configProps.getIvao().getOauth().getGrantType());
        oauthData.add("client_id", configProps.getIvao().getOauth().getClientId());
        oauthData.add("client_secret", configProps.getIvao().getOauth().getClientSecret());
        oauthData.add("scope", configProps.getIvao().getOauth().getScope());

        return oauthData;
    }

    protected Consumer<HttpHeaders> getRequestHeaders(final String authToken, final HttpHeaders additionalHeaders) {
        return httpHeaders -> {
            if (StringUtils.isNotBlank(authToken)) {
                httpHeaders.add("Authorization", "Bearer " + authToken);
            }

            httpHeaders.addAll(additionalHeaders);
        };
    }

    protected Mono<? extends Throwable> onUnAuthorised(final ClientResponse clientResponse) {
        return clientResponse.toBodilessEntity()
                .doOnNext(next -> log.warn("Unauthorised response received. Refreshing token"))
                .flatMap(response -> getToken(true))
                .flatMap(authToken -> Mono.error(new AuthenticationException("Could not authenticate with Ivao.")));
    }
}
