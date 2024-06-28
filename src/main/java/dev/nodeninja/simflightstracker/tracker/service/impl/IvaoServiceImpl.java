package dev.nodeninja.simflightstracker.tracker.service.impl;

import dev.nodeninja.simflightstracker.api.v2.model.AirTrafficController;
import dev.nodeninja.simflightstracker.api.v2.model.Aircraft;
import dev.nodeninja.simflightstracker.api.v2.model.Flight;
import dev.nodeninja.simflightstracker.tracker.adapter.ivao.IvaoAdapter;
import dev.nodeninja.simflightstracker.tracker.mapper.TrackerMapper;
import dev.nodeninja.simflightstracker.api.v2.model.FlightSummary;
import dev.nodeninja.simflightstracker.api.v2.model.IvaoLiveData;
import dev.nodeninja.simflightstracker.tracker.service.IvaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IvaoServiceImpl implements IvaoService {
    private final IvaoAdapter ivaoAdapter;
    private final TrackerMapper mapper;

    @Override
    public IvaoLiveData liveData() {
        var flights = ivaoAdapter.flights();
        var controllers = ivaoAdapter.controllers();

        var summarizedFlights = flights.stream().map(mapper::ivaoFlightToSummary).toList();
        var summarizedControllers = controllers.stream().map(mapper::ivaoAtcToSummary).toList();

        return IvaoLiveData.builder()
                .flights(summarizedFlights)
                .controllers(summarizedControllers)
                .build();
    }

    @Override
    public Flight flightDetails(String callsign) {
        var flights = ivaoAdapter.flights();

        var foundFlight = flights.stream().filter(flight -> flight.getCallsign().equals(callsign)).findFirst().orElse(null);

        if (foundFlight == null) { return null; }

        return mapper.ivaoFlightToGeneric(foundFlight);
    }

    @Override
    public AirTrafficController atcDetails(String callsign) {
        var controllers = ivaoAdapter.controllers();

        var foundController = controllers.stream().filter(controller -> controller.getCallsign().equals(callsign)).findFirst().orElse(null);

        if (foundController == null) { return null; }

        return mapper.ivaoControllerToGeneric(foundController);
    }

    @Override
    public List<FlightSummary> getFlights() {
        var flights = ivaoAdapter.flights();

        return flights.stream().map(mapper::ivaoFlightToSummary).toList();
    }

    @Override
    public Aircraft aircraftDetails(String aircraftId) {
        var aircraftDetails = ivaoAdapter.aircraftDetails(aircraftId);

        if (aircraftDetails == null) { return null; }

        return mapper.ivaoAircraftToGeneric(aircraftDetails);
    }
}
