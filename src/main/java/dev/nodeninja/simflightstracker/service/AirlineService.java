package dev.nodeninja.simflightstracker.service;

import dev.nodeninja.simflightstracker.model.Airline;

import java.util.Optional;

public interface AirlineService {
    /**
     * Get airline details matching the provided 3 letter ICAO id
     * @param icaoId 3 letter code representing airline ICAO ID
     * @return Airline details or null
     */
    Optional<Airline> getAirlineDetails(String icaoId);
}
