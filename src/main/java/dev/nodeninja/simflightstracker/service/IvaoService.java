package dev.nodeninja.simflightstracker.service;

import dev.nodeninja.simflightstracker.model.AirTrafficController;
import dev.nodeninja.simflightstracker.model.Flight;
import dev.nodeninja.simflightstracker.model.FlightSummary;
import dev.nodeninja.simflightstracker.model.ivao.response.IvaoLiveData;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface IvaoService {
    /**
     * Get a list of currently online flights and controllers
     * @return A list of online flights and controllers
     */
    Mono<IvaoLiveData> liveData();

    /**
     * Get Flight details matching provided Flight callsign
     * @param callSign Flight callsign
     * @return Full Flight details matching provided callsign
     */
    Mono<Optional<Flight>> flightDetails(String callSign);

    /**
     * Get atc details matching provided atc callsign
     * @param callSign ATC callsign
     * @return Full ATC details matching provided callsign
     */
    Mono<Optional<AirTrafficController>> atcDetails(String callSign);

    /**
     * Get all online flight summaries
     * @return A list of live flight summaries
     */
    Mono<List<FlightSummary>> getFlights();
}
