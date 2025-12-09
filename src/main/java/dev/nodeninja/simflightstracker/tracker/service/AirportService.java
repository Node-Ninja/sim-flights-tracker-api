package dev.nodeninja.simflightstracker.tracker.service;

import java.util.List;
import java.util.Optional;

import dev.nodeninja.simflightstracker.api.v2.model.Airport;

public interface AirportService {
    Optional<Airport> getAirportDetails(String icao);
    List<Airport> searchAirports(String query);
}
