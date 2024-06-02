package dev.nodeninja.simflightstracker.controller;

import dev.nodeninja.simflightstracker.model.AirTrafficController;
import dev.nodeninja.simflightstracker.model.EventSummary;
import dev.nodeninja.simflightstracker.model.Flight;
import dev.nodeninja.simflightstracker.model.vatsim.VatsimEvent;
import dev.nodeninja.simflightstracker.model.vatsim.VatsimTransceiver;
import dev.nodeninja.simflightstracker.model.vatsim.response.VatsimLiveData;
import dev.nodeninja.simflightstracker.service.VatsimService;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2/vatsim")
public class VatsimController {
    private final VatsimService vatsimService;

    public VatsimController(VatsimService vatsimService) {
        this.vatsimService = vatsimService;
    }

    @PostMapping("/live-data")
    public Mono<VatsimLiveData> getLiveData() {
        return vatsimService.vatsimLiveData();
    }

    @PostMapping("/flights/{callSign}")
    public Mono<Optional<Flight>> getLiveData(@NotNull @PathVariable String callSign) {
        return vatsimService.flightDetails(callSign);
    }

    @PostMapping("/atc/{callSign}")
    public Mono<AirTrafficController> getAtcDetails(@NotNull @PathVariable String callSign) {
        return vatsimService.atcDetails(callSign);
    }

    @PostMapping("/metar/{icaoId}")
    public Mono<String> getMetar(@NotNull @PathVariable String icaoId) {
        return vatsimService.metarByAirportIcaoId(icaoId);
    }

    @GetMapping("/events")
    public Mono<List<EventSummary>> getEvents() {
        return vatsimService.events();
    }

    @GetMapping("/events/{eventId}")
    public Mono<VatsimEvent> getEventDetails(@NotNull @PathVariable String eventId) {
        return vatsimService.eventDetails(eventId);
    }

    @PostMapping("/transceivers")
    public Mono<List<VatsimTransceiver>> getTransceivers() {
        return vatsimService.transceivers();
    }
}
