package dev.nodeninja.simflightstracker.tracker.service.impl;

import dev.nodeninja.simflightstracker.api.v2.model.*;
import dev.nodeninja.simflightstracker.exceptions.BusinessException;
import dev.nodeninja.simflightstracker.tracker.component.IvaoLiveDataCache;
import dev.nodeninja.simflightstracker.tracker.external.IvaoClient;
import dev.nodeninja.simflightstracker.tracker.http.client.AuthenticatedRestClient;
import dev.nodeninja.simflightstracker.tracker.http.model.IDProvider;
import dev.nodeninja.simflightstracker.tracker.mapper.TrackerMapper;
import dev.nodeninja.simflightstracker.tracker.service.IvaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IvaoServiceImpl implements IvaoService {
    private final TrackerMapper mapper;
    private final AuthenticatedRestClient authenticatedRestClient;
    private final IvaoClient ivaoClient;
    private final IvaoLiveDataCache ivaoLiveDataCache;

    @Override
    public IvaoLiveData liveData() {
        try {
            var flights = ivaoLiveDataCache.getIvaoFlights();
            var controllers = ivaoLiveDataCache.getIvaoControllers();

            var summarizedFlights = flights.stream().map(mapper::ivaoFlightToSummary).toList();
            var summarizedControllers = controllers.stream().map(mapper::ivaoAtcToSummary).toList();

            return IvaoLiveData.builder()
                    .flights(summarizedFlights)
                    .controllers(summarizedControllers)
                    .build();

        } catch (Exception e) {
            throw new BusinessException(
                    "Could not get Ivao live data",
                    "LIVE_DATA_ERROR",
                    HttpStatus.SC_BAD_REQUEST);
        }
    }

    @Override
    public Flight flightDetails(String callSign) {
        try {
            var flights = ivaoLiveDataCache.getIvaoFlights();

            var foundFlight = flights.stream().filter(flight -> flight.getCallsign().equals(callSign)).findFirst().orElse(null);

            if (foundFlight == null) { return null; }

            return mapper.ivaoFlightToGeneric(foundFlight);
        } catch (Exception e) {
            throw new BusinessException(
                    "Could not get Ivao flight data",
                    "LIVE_DATA_ERROR",
                    HttpStatus.SC_BAD_REQUEST);
        }
    }

    @Override
    public AirTrafficController atcDetails(String callSign) {
        try {
            var controllers = ivaoLiveDataCache.getIvaoControllers();

            var foundController = controllers.stream().filter(controller -> controller.getCallsign().equals(callSign)).findFirst().orElse(null);

            if (foundController == null) { return null; }

            return mapper.ivaoControllerToGeneric(foundController);
        } catch (Exception e) {
            throw new BusinessException(
                    "Could not get Ivao controller data",
                    "LIVE_DATA_ERROR",
                    HttpStatus.SC_BAD_REQUEST);
        }
    }

    @Override
    public List<FlightSummary> getFlights() {
        try {
            var flights = ivaoLiveDataCache.getIvaoFlights();

            return flights.stream().map(mapper::ivaoFlightToSummary).toList();
        } catch (Exception e) {
            throw new BusinessException(
                    "Could not get Ivao flights data",
                    "LIVE_DATA_ERROR",
                    HttpStatus.SC_BAD_REQUEST);
        }
    }

    @Override
    public Aircraft aircraftDetails(String aircraftId) {
        return authenticatedRestClient.requestWithRetry(IDProvider.IVAO, httpHeaders -> {
           try {
               var aircraftDetails = ivaoClient.getAircraftDetails(httpHeaders, aircraftId);

               if (aircraftDetails == null) { return null; }

               return mapper.ivaoAircraftToGeneric(aircraftDetails);
           } catch (Exception e) {
               throw new BusinessException(
                       "Could not get Ivao aircraft data",
                       "LIVE_DATA_ERROR",
                       HttpStatus.SC_BAD_REQUEST);
           }
        });
    }
}
