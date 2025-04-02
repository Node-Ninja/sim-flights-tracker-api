package dev.nodeninja.simflightstracker.tracker.service.impl;

import dev.nodeninja.simflightstracker.api.v2.model.*;
import dev.nodeninja.simflightstracker.tracker.external.IvaoClient;
import dev.nodeninja.simflightstracker.tracker.http.client.AuthenticatedRestClient;
import dev.nodeninja.simflightstracker.tracker.http.model.IDProvider;
import dev.nodeninja.simflightstracker.tracker.mapper.TrackerMapper;
import dev.nodeninja.simflightstracker.tracker.service.IvaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IvaoServiceImpl implements IvaoService {
    private final TrackerMapper mapper;
    private final AuthenticatedRestClient authenticatedRestClient;
    private final IvaoClient ivaoClient;

    @Override
    public IvaoLiveData liveData() {

        return authenticatedRestClient.requestWithRetry(IDProvider.IVAO, httpHeaders -> {
           try {
               var flights = ivaoClient.getFlights(httpHeaders);
               var controllers = ivaoClient.getControllers(httpHeaders);

               var summarizedFlights = flights.stream().map(mapper::ivaoFlightToSummary).toList();
               var summarizedControllers = controllers.stream().map(mapper::ivaoAtcToSummary).toList();

               return IvaoLiveData.builder()
                       .flights(summarizedFlights)
                       .controllers(summarizedControllers)
                       .build();

           } catch (Exception e) {
               throw new RuntimeException(e);
           }
        });
    }

    @Override
    public Flight flightDetails(String callSign) {
        return authenticatedRestClient.requestWithRetry(IDProvider.IVAO, httpHeaders -> {
            try {
                var flights = ivaoClient.getFlights(httpHeaders);

                var foundFlight = flights.stream().filter(flight -> flight.getCallsign().equals(callSign)).findFirst().orElse(null);

                if (foundFlight == null) { return null; }

                return mapper.ivaoFlightToGeneric(foundFlight);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public AirTrafficController atcDetails(String callSign) {
        return authenticatedRestClient.requestWithRetry(IDProvider.IVAO, httpHeaders -> {
            try {
                var controllers = ivaoClient.getControllers(httpHeaders);

                var foundController = controllers.stream().filter(controller -> controller.getCallsign().equals(callSign)).findFirst().orElse(null);

                if (foundController == null) { return null; }

                return mapper.ivaoControllerToGeneric(foundController);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public List<FlightSummary> getFlights() {
        return authenticatedRestClient.requestWithRetry(IDProvider.IVAO, httpHeaders -> {
           try {
               var flights = ivaoClient.getFlights(httpHeaders);

               return flights.stream().map(mapper::ivaoFlightToSummary).toList();
           } catch (Exception e) {
               throw new RuntimeException(e);
           }
        });
    }

    @Override
    public Aircraft aircraftDetails(String aircraftId) {
        return authenticatedRestClient.requestWithRetry(IDProvider.IVAO, httpHeaders -> {
           try {
               var aircraftDetails = ivaoClient.getAircraftDetails(httpHeaders, aircraftId);

               if (aircraftDetails == null) { return null; }

               return mapper.ivaoAircraftToGeneric(aircraftDetails);
           } catch (Exception e) {
               throw new RuntimeException(e);
           }
        });
    }
}
