package dev.nodeninja.simflightstracker.tracker.service;

import dev.nodeninja.simflightstracker.api.v2.model.*;
import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.FlightPlanHistoryItem;
import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.VatsimFlightsHistory;
import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.VatsimUserHours;

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

    /**
     *
     * @param vatsimId Vatsim ID to fetch flights history for
     * @param offset Offset integer used for pagination/paging
     * @return A List of flight history items, with count to determine paging
     */
    VatsimFlightsHistory flightsHistory(String vatsimId, Integer offset);

    /**
     *
     * @param vatsimId Vatsim ID to fetch flights history for
     * @return A List of flight plan history items
     */
    List<FlightPlanHistoryItem> flightPlanHistory(String vatsimId);

    FlightTrack getFlightTrack(String callsign);

    VatsimUserHours getUserHours(String vatsimId);

    String startAuth(String simNetwork);

    boolean patchAuth(String authCode, String state);

    AuthedUserDetails getAuthedUserDetails(String authId);

    void destroyVatsimRecord(String authId);

    VatsimATCHistory atcHistory(String vatsimId);
}
