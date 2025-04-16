package dev.nodeninja.simflightstracker.api.v2.controller;

import dev.nodeninja.simflightstracker.api.v2.model.*;
import dev.nodeninja.simflightstracker.tracker.service.IvaoService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/ivao")
public class IvaoController {
    private final IvaoService ivaoService;

    @PostMapping("/live-data")
    public IvaoLiveData getLiveData() {
        return this.ivaoService.liveData();
    }

    @PostMapping("/flights/{callsign}")
    public Flight getFlightDetails(@PathVariable String callsign) {
        return ivaoService.flightDetails(callsign);
    }

    @PostMapping("/atc/{callsign}")
    public AirTrafficController getAtcDetails(@PathVariable String callsign) {
        return ivaoService.atcDetails(callsign);
    }

    @PostMapping("/flights")
    public List<FlightSummary> getFlights() {
        return ivaoService.getFlights();
    }

        @PostMapping("/events")
    public List<EventSummary> getEvents() {
        return ivaoService.events();
    }

    @PostMapping("/events/{eventId}")
    public EventDetails getEventDetails(@NotNull @PathVariable String eventId) {
        return ivaoService.eventDetails(eventId);
    }
}
