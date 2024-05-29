package dev.nodeninja.simflightstracker.service.impl;

import dev.nodeninja.simflightstracker.model.Airport;
import dev.nodeninja.simflightstracker.repository.AirportRepository;
import dev.nodeninja.simflightstracker.service.AirportService;
import org.springframework.stereotype.Service;

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
}
