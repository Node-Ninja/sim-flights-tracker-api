package dev.nodeninja.simflightstracker.tracker.service.impl;

import dev.nodeninja.simflightstracker.api.v2.model.Airport;
import dev.nodeninja.simflightstracker.tracker.repository.AirportRepository;
import dev.nodeninja.simflightstracker.tracker.service.AirportService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirportServiceImpl implements AirportService {
    private final AirportRepository airportRepository;

    public AirportServiceImpl(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Override
    public Optional<Airport> getAirportDetails(String icao) {
        return airportRepository.findAirportByIdent(icao);
    }

    @Override
    public List<Airport> searchAirports(String query) {
        return airportRepository.findBy(query);
    }
}
