package dev.nodeninja.simflightstracker.tracker.service;

import dev.nodeninja.simflightstracker.api.v2.model.AirTrafficController;
import dev.nodeninja.simflightstracker.api.v2.model.EventSummary;
import dev.nodeninja.simflightstracker.api.v2.model.Flight;
import dev.nodeninja.simflightstracker.api.v2.model.VatsimEvent;
import dev.nodeninja.simflightstracker.api.v2.model.VatsimTransceiver;
import dev.nodeninja.simflightstracker.api.v2.model.VatsimLiveData;

import java.util.List;

public interface VatsimService {
    /**
     * Find and return METAR information for airport matching provided ICAO ID
     * @param icaoId 4 letter airport ICAO ID
     * @return a string detailing metar information
     */
    String metarByAirportIcaoId(String icaoId);

    /**
     * Get a summary of live flights and controllers.
     * @return Summary lists of flights and controllers;
     */
    VatsimLiveData vatsimLiveData();

    /**
     * Search for vatsim flight matching the provided callsign;
     * @param callSign callsign identifying the flight to look for
     * @return Vatsim flight details matching provided callsign, or null
     */
    Flight flightDetails(String callSign);

    /**
     * Get ATC details matching the provided callsign;
     * @param callSign callsign identifying the controller to look for
     * @return Vatsim ATC details
     */
    AirTrafficController atcDetails(String callSign);

    /**
     * Get a list of available vatsim event summaries
     * @return A list of vatsim event summaries
     */
    List<EventSummary> events();

    /**
     * Get event details for the provided event ID
     * @param eventId Vatsim event ID
     * @return Full event details matching provided event ID
     */
    VatsimEvent eventDetails(String eventId);

    /**
     * Get a list of transceivers for Vatsim
     * @return List of transceivers
     */
    List<VatsimTransceiver> transceivers();
}
