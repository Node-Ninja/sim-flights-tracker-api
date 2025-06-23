package dev.nodeninja.simflightstracker.api.v2.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.nodeninja.simflightstracker.api.v2.model.Airport;
import dev.nodeninja.simflightstracker.tracker.service.AirportService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/airport")
public class AirportController {
    private final AirportService airportService;

    @Operation(
            summary = "Get airport details",
            description = "Get airport details for airport matching the provided ICAO ID")
    @GetMapping("/{icaoId}")
    public ResponseEntity<Optional<Airport>> getAirportDetails(@PathVariable String icaoId) {
        return new ResponseEntity<>(airportService.getAirportDetails(icaoId), HttpStatus.OK);
    }

    @PostMapping("/{query}")
    ResponseEntity<List<Airport>> searchAirports(@PathVariable String query) {
        return new ResponseEntity<>(airportService.searchAirports(query), HttpStatus.OK);
    }
}
