package dev.nodeninja.simflightstracker.controller;

import dev.nodeninja.simflightstracker.model.Airline;
import dev.nodeninja.simflightstracker.service.AirlineService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v2/airline")
public class AirlineController {
    private final AirlineService airlineService;

    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @Operation(
        summary = "Get airline details",
        description = "Get airline details for airport matching the provided ICAO ID")
    @GetMapping("/{icaoId}")
    public ResponseEntity<Optional<Airline>> getAirlineDetails(@PathVariable String icaoId) {
        return new ResponseEntity<>((airlineService.getAirlineDetails(icaoId)), HttpStatus.OK);
    }
}
