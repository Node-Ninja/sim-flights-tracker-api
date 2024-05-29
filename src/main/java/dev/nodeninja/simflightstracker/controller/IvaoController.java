package dev.nodeninja.simflightstracker.controller;

import dev.nodeninja.simflightstracker.model.AirTrafficController;
import dev.nodeninja.simflightstracker.model.Flight;
import dev.nodeninja.simflightstracker.model.FlightSummary;
import dev.nodeninja.simflightstracker.model.ivao.response.IvaoLiveData;
import dev.nodeninja.simflightstracker.service.IvaoService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2/ivao")
public class IvaoController {
    private final IvaoService ivaoService;

    public IvaoController(IvaoService ivaoService) {
        this.ivaoService = ivaoService;
    }

    @PostMapping("/live-data")
    public Mono<IvaoLiveData> getLiveData() {
        return this.ivaoService.liveData();
    }

    @PostMapping("/flights/{callsign}")
    public Mono<Optional<Flight>> getFlightDetails(@PathVariable String callsign) {
        return ivaoService.flightDetails(callsign);
    }

    @PostMapping("/atc/{callsign}")
    public Mono<Optional<AirTrafficController>> getAtcDetails(@PathVariable String callsign) {
        return ivaoService.atcDetails(callsign);
    }

    @PostMapping("/flights")
    public Mono<List<FlightSummary>> getFlights() {
        return ivaoService.getFlights();
    }
}
