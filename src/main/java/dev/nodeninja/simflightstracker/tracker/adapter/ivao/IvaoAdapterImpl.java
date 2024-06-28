package dev.nodeninja.simflightstracker.tracker.adapter.ivao;

import dev.nodeninja.simflightstracker.config.ApplicationConfigProperties;
import dev.nodeninja.simflightstracker.config.IvaoAuthenticatedWebClient;
import dev.nodeninja.simflightstracker.tracker.adapter.ivao.model.AircraftDetailsResponse;
import dev.nodeninja.simflightstracker.tracker.adapter.ivao.model.IvaoAtc;
import dev.nodeninja.simflightstracker.tracker.adapter.ivao.model.IvaoFlight;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class IvaoAdapterImpl implements  IvaoAdapter {
    private final ApplicationConfigProperties configProps;
    private final IvaoAuthenticatedWebClient authenticatedWebClient;

    @Override
    public List<IvaoFlight> flights() throws HttpClientErrorException {
        var endpoint = configProps.getIvao().getHost().getV2() + "/tracker/now/pilots";

        return authenticatedWebClient
                .sendRequest(
                        endpoint,
                        HttpMethod.GET,
                        HttpHeaders.EMPTY,
                        null,
                        null,
                        new ParameterizedTypeReference<List<IvaoFlight>>() {}
                )
                .block();
    }

    @Override
    public List<IvaoAtc> controllers() throws HttpClientErrorException {
        return authenticatedWebClient
                .sendRequest(
                        configProps.getIvao().getHost().getV2() + "/tracker/now/atc",
                        HttpMethod.GET,
                        HttpHeaders.EMPTY,
                        null,
                        null,
                        new ParameterizedTypeReference<List<IvaoAtc>>() {}
                )
                .block();
    }

    @Override
    public AircraftDetailsResponse aircraftDetails(String aircraftId) throws HttpClientErrorException {
        var endpoint = configProps.getIvao().getHost().getV2() + "/aircrafts/" + aircraftId;

        return authenticatedWebClient
                .sendRequest(
                        endpoint,
                        HttpMethod.GET,
                        HttpHeaders.EMPTY,
                        null,
                        null,
                        AircraftDetailsResponse.class
                )
                .block();
    }
}

