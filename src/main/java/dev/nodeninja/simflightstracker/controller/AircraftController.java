package dev.nodeninja.simflightstracker.controller;

import dev.nodeninja.simflightstracker.model.Aircraft;
import dev.nodeninja.simflightstracker.service.IvaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/api/v2/aircraft")
@RequiredArgsConstructor
public class AircraftController {
    private final IvaoService ivaoService;

    @GetMapping(value = "/{aircraftId}")
    public Mono<Optional<Aircraft>> getAircraft(@PathVariable String aircraftId) {
        return ivaoService.aircraftDetails(aircraftId);
    }
}
