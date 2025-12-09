package dev.nodeninja.simflightstracker.api.v2.controller;

import dev.nodeninja.simflightstracker.api.v2.model.Aircraft;
import dev.nodeninja.simflightstracker.tracker.service.IvaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/aircraft")
@RequiredArgsConstructor
public class AircraftController {
    private final IvaoService ivaoService;

    @GetMapping(value = "/{aircraftId}")
    public Aircraft getAircraft(@PathVariable String aircraftId) {
        return ivaoService.aircraftDetails(aircraftId);
    }
}
