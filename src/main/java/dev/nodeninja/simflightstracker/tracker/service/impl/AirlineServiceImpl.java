package dev.nodeninja.simflightstracker.tracker.service.impl;

import dev.nodeninja.simflightstracker.api.v2.model.Airline;
import dev.nodeninja.simflightstracker.tracker.repository.AirlineRepository;
import dev.nodeninja.simflightstracker.tracker.service.AirlineService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class AirlineServiceImpl implements AirlineService {
    private final AirlineRepository airlineRepository;

    public AirlineServiceImpl(AirlineRepository _airlineRepository) {
        this.airlineRepository = _airlineRepository;
    }

    @Override
    public Optional<Airline> getAirlineDetails(String icao) {
        return airlineRepository.findAirlineByIcao(icao);
    }
}
