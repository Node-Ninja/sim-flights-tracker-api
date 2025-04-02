package dev.nodeninja.simflightstracker.tracker.service.impl;

import dev.nodeninja.simflightstracker.api.v2.model.*;
import dev.nodeninja.simflightstracker.config.ApplicationConfigProperties;
import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.FlightPlanHistoryItem;
import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.VatsimFlightsHistory;
import dev.nodeninja.simflightstracker.tracker.external.VatsimClient;
import dev.nodeninja.simflightstracker.tracker.mapper.TrackerMapper;
import dev.nodeninja.simflightstracker.tracker.service.VatsimService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VatsimServiceImpl implements VatsimService {
    private final TrackerMapper mapper;
    private final VatsimClient vatsimClient;

    private final ApplicationConfigProperties configProps;


    @Override
    public VatsimLiveData vatsimLiveData() {
        try {
            var response = vatsimClient.getLiveData();

            return VatsimLiveData.builder()
                    .flights(response.getPilots().stream().map(mapper::vatsimFlightToSummary).collect(Collectors.toList()))
                    .controllers(response.getControllers().stream().map(mapper::vatsimAtcToSummary).collect(Collectors.toList()))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Flight flightDetails(String callSign) {
        try {
            var response = vatsimClient.getLiveData();

            var flights = response.getPilots();
            var foundFlight = flights.stream()
                    .filter(flight -> callSign.equals(flight.getCallsign()))
                    .findFirst()
                    .orElse(null);

            if (foundFlight == null) { return null; }

            return mapper.vatsimFlightToGeneric(foundFlight);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AirTrafficController atcDetails(String callSign) {
        try {
            var response = vatsimClient.getLiveData();

            var controllers = response.getControllers();

            var foundController = controllers.stream().filter(controller -> controller.getCallsign().equals(callSign)).findFirst().orElse(null);

            if (foundController == null) { return null; }

            return mapper.vatsimControllerToGeneric(foundController);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String metarByAirportIcaoId(String icaoId) {
        try {
            URI endpoint = URI.create(configProps.getVatsim().getHost().getMetar()  + String.format("/%s", icaoId));

            return vatsimClient.metarByIcaoId(endpoint);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<EventSummary> events() {
        URI endpoint = URI.create(configProps.getVatsim().getHost().getV2() + "/events/latest");

        try {
            var response = vatsimClient.getAllEvents(endpoint);
            return response.getData().stream().map(mapper::vatsimEventToSummary).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public VatsimEvent eventDetails(String eventId) {
        URI endpoint = URI.create(configProps.getVatsim().getHost().getV2() + String.format("/events/view/%s", eventId));

        try {
            var response = vatsimClient.eventDetails(endpoint);

            return response.getData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<VatsimTransceiver> transceivers() {
        try {
            var allowedStations = List.of("DEL", "GND", "TWR", "APP", "DEP", "CTR");

            var response = vatsimClient.getAllTransceivers();

            return response
                    .stream()
                    .filter(ts1 -> allowedStations.contains(
                            StringUtils.right(
                                    ts1.getCallsign(),
                                    3))).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public VatsimFlightsHistory flightsHistory(String vatsimId, Integer offset) {
        URI endpoint = URI.create(configProps.getVatsim().getHost().getCore()  + "/members/" + vatsimId + "/history?limit=" + 50);

        try {
            return vatsimClient.getFlightsHistory(endpoint);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<FlightPlanHistoryItem> flightPlanHistory(String vatsimId) {
        URI endpoint = URI.create(configProps.getVatsim().getHost().getCore()  + "/members/" + vatsimId + "/flightplans");

        try {
            return vatsimClient.flightPlanHistory(endpoint);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
