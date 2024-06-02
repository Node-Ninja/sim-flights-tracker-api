package dev.nodeninja.simflightstracker.service.impl;

import dev.nodeninja.simflightstracker.config.ApplicationConfigProperties;
import dev.nodeninja.simflightstracker.exceptions.FlightNotFoundException;
import dev.nodeninja.simflightstracker.exceptions.GenericNotFoundException;
import dev.nodeninja.simflightstracker.model.AirTrafficController;
import dev.nodeninja.simflightstracker.model.EventSummary;
import dev.nodeninja.simflightstracker.model.Flight;
import dev.nodeninja.simflightstracker.model.dto.vatsim.VatsimDataApiResponse;
import dev.nodeninja.simflightstracker.model.dto.vatsim.VatsimEventDetailsApiResponse;
import dev.nodeninja.simflightstracker.model.dto.vatsim.VatsimEventsApiResponse;
import dev.nodeninja.simflightstracker.model.vatsim.VatsimAtc;
import dev.nodeninja.simflightstracker.model.vatsim.VatsimEvent;
import dev.nodeninja.simflightstracker.model.vatsim.VatsimFlight;
import dev.nodeninja.simflightstracker.model.vatsim.VatsimTransceiver;
import dev.nodeninja.simflightstracker.model.vatsim.response.VatsimLiveData;
import dev.nodeninja.simflightstracker.service.VatsimService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VatsimServiceImpl implements VatsimService {
    private final ApplicationConfigProperties configProps;
    private final WebClient webClient;


    @Override
    public Mono<VatsimLiveData> vatsimLiveData() {
        return webClient
                .get()
                .uri(configProps.getVatsim().getHost().getV3()  + "/vatsim-data.json")
                .retrieve()
                .bodyToMono(VatsimDataApiResponse.class)
                .map(VatsimLiveData::mapVatsimLiveDataSummary)
                .onErrorMap(exception -> new RuntimeException());
    }

    @Override
    public Mono<Optional<Flight>> flightDetails(String callSign) {
        return webClient
                .get()
                .uri(configProps.getVatsim().getHost().getV3()  +"/vatsim-data.json")
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, res -> Mono.error(new FlightNotFoundException("Not Found")))
                .bodyToMono(VatsimDataApiResponse.class)
                .mapNotNull(response -> {
                    var flights = response.getPilots();
                    var foundFlight = flights.stream()
                            .filter(flight -> callSign.equals(flight.getCallsign()))
                            .findFirst();

                    if (foundFlight.isEmpty()) return null;

                    return foundFlight.map(VatsimFlight::toGenericFlight).orElse(null);
                })
                .onErrorMap(exception -> exception).singleOptional();
    }

    @Override
    public Mono<AirTrafficController> atcDetails(String callSign) {
        return webClient
                .get()
                .uri(configProps.getVatsim().getHost().getV3()  +"/vatsim-data.json")
                .retrieve()
                .bodyToMono(VatsimDataApiResponse.class)
                .mapNotNull(response -> {
                    var atc = response.getControllers();
                    var found = atc.stream().filter(controller -> controller.getCallsign().equals(callSign)).findFirst().orElse(null);
                    if (found != null) {
                        return found.toGenericController();
                    }
                    return null;
                })
                .onErrorMap(exception -> exception);
    }

    @Override
    public Mono<String> metarByAirportIcaoId(String icaoId) {
        return webClient
                .get()
                .uri(configProps.getVatsim().getHost().getMetar()  + String.format("/%s", icaoId))
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> response)
                .onErrorMap(exception -> new GenericNotFoundException("Metar not found"));
    }

    @Override
    public Mono<List<EventSummary>> events() {
        return webClient
                .get()
                .uri(configProps.getVatsim().getHost().getV2() + "/events/latest")
                .retrieve()
                .bodyToMono(VatsimEventsApiResponse.class)
                .map(response -> response.getData().stream().map(VatsimEvent::toSummary).toList())
                .onErrorMap(ex -> ex);
    }

    @Override
    public Mono<VatsimEvent> eventDetails(String eventId) {
        return webClient
                .get()
                .uri(configProps.getVatsim().getHost().getV2() + String.format("/events/view/%s", eventId))
                .retrieve()
                .bodyToMono(VatsimEventDetailsApiResponse.class)
                .map(VatsimEventDetailsApiResponse::getData)
                .onErrorMap(ex -> ex);
    }

    @Override
    public Mono<List<VatsimTransceiver>> transceivers() {
        var allowedStations = List.of("DEL", "GND", "TWR", "APP", "CTR");

        return webClient
                .get()
                .uri(configProps.getVatsim().getHost().getV3() + "/transceivers-data.json")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<VatsimTransceiver>>() {})
                .map(transceivers -> transceivers.stream().filter(ts -> allowedStations.contains(StringUtils.right(ts.getCallsign(), 3))).toList())
                .onErrorMap(ex -> ex);
    }
}
