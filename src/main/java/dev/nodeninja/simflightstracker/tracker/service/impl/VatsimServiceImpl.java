package dev.nodeninja.simflightstracker.tracker.service.impl;

import dev.nodeninja.simflightstracker.api.v2.model.AirTrafficController;
import dev.nodeninja.simflightstracker.api.v2.model.EventSummary;
import dev.nodeninja.simflightstracker.api.v2.model.Flight;
import dev.nodeninja.simflightstracker.api.v2.model.VatsimEvent;
import dev.nodeninja.simflightstracker.api.v2.model.VatsimLiveData;
import dev.nodeninja.simflightstracker.api.v2.model.VatsimTransceiver;
import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.VatsimAdapter;
import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.VatsimFlightsHistory;
import dev.nodeninja.simflightstracker.tracker.mapper.TrackerMapper;
import dev.nodeninja.simflightstracker.tracker.service.VatsimService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VatsimServiceImpl implements VatsimService {
    private final VatsimAdapter vatsimAdapter;
    private final TrackerMapper mapper;


    @Override
    public VatsimLiveData vatsimLiveData() {
        var response = vatsimAdapter.liveData();

        return VatsimLiveData.builder()
                .flights(response.getPilots().stream().map(mapper::vatsimFlightToSummary).collect(Collectors.toList()))
                .controllers(response.getControllers().stream().map(mapper::vatsimAtcToSummary).collect(Collectors.toList()))
                .build();
    }

    @Override
    public Flight flightDetails(String callSign) {
        var response = vatsimAdapter.liveData();
        var flights = response.getPilots();
        var foundFlight = flights.stream()
                .filter(flight -> callSign.equals(flight.getCallsign()))
                .findFirst()
                .orElse(null);

        if (foundFlight == null) { return null; }

        return mapper.vatsimFlightToGeneric(foundFlight);
    }

    @Override
    public AirTrafficController atcDetails(String callSign) {
        var response = vatsimAdapter.liveData();
        var controllers = response.getControllers();

        var foundController = controllers.stream().filter(controller -> controller.getCallsign().equals(callSign)).findFirst().orElse(null);

        if (foundController == null) { return null; }

        return mapper.vatsimControllerToGeneric(foundController);
    }

    @Override
    public String metarByAirportIcaoId(String icaoId) {
        return vatsimAdapter.metarByIcaoId(icaoId);
    }

    @Override
    public List<EventSummary> events() {
        var response = vatsimAdapter.allEvents();
        return response.getData().stream().map(mapper::vatsimEventToSummary).toList();
    }

    @Override
    public VatsimEvent eventDetails(String eventId) {
        var response = vatsimAdapter.eventDetails(eventId);

        return response.getData();
    }

    @Override
    public List<VatsimTransceiver> transceivers() {
        var allowedStations = List.of("DEL", "GND", "TWR", "APP", "CTR");

        var response = vatsimAdapter.allTransceivers();

        return response
                .stream()
                .filter(ts1 -> allowedStations.contains(
                        StringUtils.right(
                                ts1.getCallsign(),
                                3))).toList();
    }

    @Override
    public VatsimFlightsHistory flightsHistory(String vatsimId, Integer offset) {
        return vatsimAdapter.flightHistory(vatsimId, offset);
    }
}
