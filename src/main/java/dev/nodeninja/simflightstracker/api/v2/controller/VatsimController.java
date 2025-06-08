package dev.nodeninja.simflightstracker.api.v2.controller;

import dev.nodeninja.simflightstracker.api.v2.model.*;
import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.FlightPlanHistoryItem;
import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.VatsimFlightsHistory;
import dev.nodeninja.simflightstracker.tracker.service.TrackUpdaterService;
import dev.nodeninja.simflightstracker.tracker.service.VatsimService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/vatsim")
public class VatsimController {
    private final VatsimService vatsimService;
    private final TrackUpdaterService trackUpdaterService;

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

    @PostMapping("/history/flights/{vatsimId}/{offset}")
    public VatsimFlightsHistory getFlightsHistory(@NotNull @PathVariable String vatsimId, @NotNull @PathVariable int offset) {
        return vatsimService.flightsHistory(vatsimId, offset);
    }

    @PostMapping("/history/plans/{vatsimId}")
    public List<FlightPlanHistoryItem> getFlightsHistory(@NotNull @PathVariable String vatsimId) {
        return vatsimService.flightPlanHistory(vatsimId);
    }

    @PostMapping("/flights/{callSign}/track")
    public FlightTrack getFlightTrack(@NotNull @PathVariable String callSign) {
        return trackUpdaterService.getTrack(callSign, "vatsim");
    }
}
