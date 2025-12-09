package dev.nodeninja.simflightstracker.tracker.service;

import java.util.Optional;

import dev.nodeninja.simflightstracker.api.v2.model.Airline;

public interface AirlineService {
    /**
     * Get airline details matching the provided 3 letter ICAO id
     * @param icaoId 3 letter code representing airline ICAO ID
     * @return Airline details or null
     */
    Optional<Airline> getAirlineDetails(String icaoId);
}
