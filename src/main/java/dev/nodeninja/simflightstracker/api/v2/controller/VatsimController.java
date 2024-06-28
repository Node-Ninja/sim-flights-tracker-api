package dev.nodeninja.simflightstracker.api.v2.controller;

import dev.nodeninja.simflightstracker.api.v2.model.VatsimEvent;
import dev.nodeninja.simflightstracker.api.v2.model.AirTrafficController;
import dev.nodeninja.simflightstracker.api.v2.model.EventSummary;
import dev.nodeninja.simflightstracker.api.v2.model.Flight;
import dev.nodeninja.simflightstracker.api.v2.model.VatsimTransceiver;
import dev.nodeninja.simflightstracker.api.v2.model.VatsimLiveData;
import dev.nodeninja.simflightstracker.tracker.service.VatsimService;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2/vatsim")
public class VatsimController {
    private final VatsimService vatsimService;

    public VatsimController(VatsimService vatsimService) {
        this.vatsimService = vatsimService;
    }

    @PostMapping("/live-data")
    public VatsimLiveData getLiveData() {
        return vatsimService.vatsimLiveData();
    }

    @PostMapping("/flights/{callSign}")
    public Flight getLiveData(@NotNull @PathVariable String callSign) {
        return vatsimService.flightDetails(callSign);
    }

    @PostMapping("/atc/{callSign}")
    public AirTrafficController getAtcDetails(@NotNull @PathVariable String callSign) {
        return vatsimService.atcDetails(callSign);
    }

    @PostMapping("/metar/{icaoId}")
    public String getMetar(@NotNull @PathVariable String icaoId) {
        return vatsimService.metarByAirportIcaoId(icaoId);
    }

    @PostMapping("/events")
    public List<EventSummary> getEvents() {
        return vatsimService.events();
    }

    @PostMapping("/events/{eventId}")
    public VatsimEvent getEventDetails(@NotNull @PathVariable String eventId) {
        return vatsimService.eventDetails(eventId);
    }

    @PostMapping("/transceivers")
    public List<VatsimTransceiver> getTransceivers() {
        return vatsimService.transceivers();
    }
}
