package dev.nodeninja.simflightstracker.tracker.service;

import dev.nodeninja.simflightstracker.api.v2.model.*;

import java.util.List;

public interface IvaoService {
    /**
     * Get a list of currently online flights and controllers
     * @return A list of online flights and controllers
     */
    IvaoLiveData liveData();

    /**
     * Get Flight details matching provided Flight callsign
     * @param callSign Flight callsign
     * @return Full Flight details matching provided callsign
     */
    Flight flightDetails(String callSign);

    /**
     * Get atc details matching provided atc callsign
     * @param callSign ATC callsign
     * @return Full ATC details matching provided callsign
     */
    AirTrafficController atcDetails(String callSign);

    /**
     * Get all online flight summaries
     * @return A list of live flight summaries
     */
    List<FlightSummary> getFlights();

    /**
     * Get aircraft details matching provided ID;
     * @param aircraftId ICAO aircraft ID or FAA Aircraft Short ID
     * @return Aircraft details
     */
    Aircraft aircraftDetails(String aircraftId);

    /**
     * Get latest Ivao events
     * @return List<IvaoEvent> List of Ivao Events
     */
    List<EventSummary> events();

    /**
     * Get Event details
     * @param eventId Event ID to get details for
     * @return IvaoEvent Ivao event details
     */
    EventDetails eventDetails(String eventId);
}
