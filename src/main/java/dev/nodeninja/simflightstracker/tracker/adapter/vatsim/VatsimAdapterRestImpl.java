package dev.nodeninja.simflightstracker.tracker.adapter.vatsim;

import dev.nodeninja.simflightstracker.api.v2.model.VatsimEvent;
import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.VatsimV3BaseResponse;
import dev.nodeninja.simflightstracker.config.ApplicationConfigProperties;
import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.VatsimDataApiResponse;
import dev.nodeninja.simflightstracker.exceptions.GenericNotFoundException;
import dev.nodeninja.simflightstracker.api.v2.model.VatsimTransceiver;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VatsimAdapterRestImpl implements VatsimAdapter {
    private final ApplicationConfigProperties configProps;
    private final WebClient webClient;

    @Override
    public VatsimDataApiResponse liveData() {
        return webClient
                .get()
                .uri(configProps.getVatsim().getHost().getV3()  + "/vatsim-data.json")
                .retrieve()
                .bodyToMono(VatsimDataApiResponse.class)
                .onErrorMap(ex -> {
                    System.out.println(ex.getMessage());
                    return new GenericNotFoundException(ex.getMessage());
                })
                .block();
    }

    @Override
    public String metarByIcaoId(String icaoId) throws HttpClientErrorException {
        return webClient
                .get()
                .uri(configProps.getVatsim().getHost().getMetar()  + String.format("/%s", icaoId))
                .retrieve()
                .bodyToMono(String.class)
                .onErrorMap(exception -> new GenericNotFoundException("Metar not found"))
                .block();
    }

    @Override
    public VatsimV3BaseResponse<List<VatsimEvent>> allEvents() throws HttpClientErrorException {
        return webClient
                .get()
                .uri(configProps.getVatsim().getHost().getV2() + "/events/latest")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<VatsimV3BaseResponse<List<VatsimEvent>>>() {})
                .onErrorMap(ex -> ex)
                .block();
    }

    @Override
    public VatsimV3BaseResponse<VatsimEvent> eventDetails(String eventId) throws HttpClientErrorException {
        return webClient
                .get()
                .uri(configProps.getVatsim().getHost().getV2() + String.format("/events/view/%s", eventId))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<VatsimV3BaseResponse<VatsimEvent>>() {})
                .onErrorMap(ex -> ex)
                .block();
    }

    @Override
    public List<VatsimTransceiver> allTransceivers() throws HttpClientErrorException {
        return webClient
                .get()
                .uri(configProps.getVatsim().getHost().getV3() + "/transceivers-data.json")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<VatsimTransceiver>>() {})
                .onErrorMap(ex -> ex)
                .block();
    }
}
