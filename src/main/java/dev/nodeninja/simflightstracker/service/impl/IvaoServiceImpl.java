package dev.nodeninja.simflightstracker.service.impl;

import dev.nodeninja.simflightstracker.config.ApplicationConfigProperties;
import dev.nodeninja.simflightstracker.config.IvaoAuthenticatedWebClient;
import dev.nodeninja.simflightstracker.model.AirTrafficController;
import dev.nodeninja.simflightstracker.model.Aircraft;
import dev.nodeninja.simflightstracker.model.Flight;
import dev.nodeninja.simflightstracker.model.FlightSummary;
import dev.nodeninja.simflightstracker.model.dto.ivao.response.AircraftDetailsResponse;
import dev.nodeninja.simflightstracker.model.ivao.IvaoAtc;
import dev.nodeninja.simflightstracker.model.ivao.IvaoFlight;
import dev.nodeninja.simflightstracker.model.ivao.response.IvaoLiveData;
import dev.nodeninja.simflightstracker.service.IvaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class IvaoServiceImpl implements IvaoService {
    private final ApplicationConfigProperties configProps;
    private final IvaoAuthenticatedWebClient authenticatedWebClient;

    @Override
    public Mono<IvaoLiveData> liveData() {

        return Mono.zip(buildPilotsRequest(), buildAtcRequest(), (flights, atc) -> IvaoLiveData.builder()
                .flights(flights.stream().map(IvaoFlight::toSummary).collect(Collectors.toList()))
                .controllers(atc.stream().map(IvaoAtc::toSummary).collect(Collectors.toList()))
                .build())
                .onErrorMap(ex -> ex);
    }

    @Override
    public Mono<Optional<Flight>> flightDetails(String callsign) {
        return authenticatedWebClient
                .sendRequest(
                        configProps.getIvao().getHost().getV2() + "/tracker/now/pilots",
                        HttpMethod.GET,
                        HttpHeaders.EMPTY,
                        null,
                        null,
                        new ParameterizedTypeReference<List<IvaoFlight>>() {}
                )
                .map(flights -> {
                    var foundFlight = flights.stream().filter(flight -> flight.getCallsign().equals(callsign)).findFirst();
                    return foundFlight.map(IvaoFlight::toGenericFlight).orElse(null);
                })
                .onErrorMap(exception -> exception).singleOptional();
    }

    @Override
    public Mono<Optional<AirTrafficController>> atcDetails(String callsign) {
        return authenticatedWebClient
                .sendRequest(
                        configProps.getIvao().getHost().getV2() + "/tracker/now/atc",
                        HttpMethod.GET,
                        HttpHeaders.EMPTY,
                        null,
                        null,
                        new ParameterizedTypeReference<List<IvaoAtc>>() {}
                )
                .mapNotNull(controllers -> {
                    var foundController =  controllers.stream().filter(controller -> controller.getCallsign().equals(callsign)).findFirst();

                    if (foundController.isPresent()) {
                        return foundController.map(IvaoAtc::toGenericController).orElse(null);
                    }
                    return null;
                })
                .onErrorMap(exception -> {
                    log.warn(" Something went wrong :: ", exception);
                    return exception;
                }).singleOptional();
    }

    @Override
    public Mono<List<FlightSummary>> getFlights() {
        return authenticatedWebClient
                .sendRequest(
                        configProps.getIvao().getHost().getV2() + "/tracker/now/pilots",
                        HttpMethod.GET,
                        HttpHeaders.EMPTY,
                        null,
                        null,
                        new ParameterizedTypeReference<List<IvaoFlight>>() {}
                )
                .map(flights -> flights.stream().map(IvaoFlight::toSummary).collect(Collectors.toList()))
                .onErrorMap(exception -> exception);

    }

    @Override
    public Mono<Optional<Aircraft>> aircraftDetails(String aircraftId) {
        return authenticatedWebClient
                .sendRequest(
                        configProps.getIvao().getHost().getV2() + "/aircrafts/" + aircraftId,
                        HttpMethod.GET,
                        HttpHeaders.EMPTY,
                        null,
                        null,
                        AircraftDetailsResponse.class
                )
                .map(AircraftDetailsResponse::mapToAircraft)
                .onErrorMap(exception -> exception).singleOptional();
    }


    private Mono<List<IvaoFlight>> buildPilotsRequest() {
        return authenticatedWebClient
                .sendRequest(
                        configProps.getIvao().getHost().getV2() + "/tracker/now/pilots",
                        HttpMethod.GET,
                        HttpHeaders.EMPTY,
                        null,
                        null,
                        new ParameterizedTypeReference<>() {}
                );
    }

    private Mono<List<IvaoAtc>> buildAtcRequest() {
        return authenticatedWebClient
                .sendRequest(
                        configProps.getIvao().getHost().getV2() + "/tracker/now/atc",
                        HttpMethod.GET,
                        HttpHeaders.EMPTY,
                        null,
                        null,
                        new ParameterizedTypeReference<>() {}
                );
    }


}
