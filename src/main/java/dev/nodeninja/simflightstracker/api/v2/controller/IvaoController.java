package dev.nodeninja.simflightstracker.api.v2.controller;

import dev.nodeninja.simflightstracker.api.v2.model.AirTrafficController;
import dev.nodeninja.simflightstracker.api.v2.model.Flight;
import dev.nodeninja.simflightstracker.api.v2.model.FlightSummary;
import dev.nodeninja.simflightstracker.api.v2.model.IvaoLiveData;
import dev.nodeninja.simflightstracker.tracker.service.IvaoService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2/ivao")
public class IvaoController {
    private final IvaoService ivaoService;

    public IvaoController(IvaoService ivaoService) {
        this.ivaoService = ivaoService;
    }

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
}
