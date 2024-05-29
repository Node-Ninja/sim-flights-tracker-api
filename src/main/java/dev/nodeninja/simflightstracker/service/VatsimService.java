package dev.nodeninja.simflightstracker.service;

import dev.nodeninja.simflightstracker.model.AirTrafficController;
import dev.nodeninja.simflightstracker.model.EventSummary;
import dev.nodeninja.simflightstracker.model.Flight;
import dev.nodeninja.simflightstracker.model.vatsim.VatsimEvent;
import dev.nodeninja.simflightstracker.model.vatsim.VatsimTransceiver;
import dev.nodeninja.simflightstracker.model.vatsim.response.VatsimLiveData;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface VatsimService {
    /**
     * Find and return METAR information for airport matching provided ICAO ID
     * @param icaoId 4 letter airport ICAO ID
     * @return a string detailing metar information
     */
    Mono<String> metarByAirportIcaoId(String icaoId);

    /**
     * Get a summary of live flights and controllers.
     * @return Summary lists of flights and controllers;
     */
    Mono<VatsimLiveData> vatsimLiveData();

    /**
     * Search for vatsim flight matching the provided callsign;
     * @param callSign callsign identifying the flight to look for
     * @return Vatsim flight details matching provided callsign, or null
     */
    Mono<Optional<Flight>> flightDetails(String callSign);

    /**
     * Get ATC details matching the provided callsign;
     * @param callSign callsign identifying the controller to look for
     * @return Vatsim ATC details
     */
    Mono<AirTrafficController> atcDetails(String callSign);

    /**
     * Get a list of available vatsim event summaries
     * @return A list of vatsim event summaries
     */
    Mono<List<EventSummary>> events();

    /**
     * Get event details for the provided event ID
     * @param eventId Vatsim event ID
     * @return Full event details matching provided event ID
     */
    Mono<VatsimEvent> eventDetails(String eventId);

    /**
     * Get a list of transceivers for Vatsim
     * @return List of transceivers
     */
    Mono<List<VatsimTransceiver>> transceivers();
}
