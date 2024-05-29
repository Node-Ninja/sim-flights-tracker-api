package dev.nodeninja.simflightstracker.service;

import dev.nodeninja.simflightstracker.model.Airport;

import java.util.Optional;

public interface AirportService {
    Optional<Airport> getAirportDetails(String icao);
}
