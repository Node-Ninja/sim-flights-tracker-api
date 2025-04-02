package dev.nodeninja.simflightstracker.tracker.external;

import dev.nodeninja.simflightstracker.tracker.adapter.ivao.model.AircraftDetailsResponse;
import dev.nodeninja.simflightstracker.tracker.adapter.ivao.model.IvaoAtc;
import dev.nodeninja.simflightstracker.tracker.adapter.ivao.model.IvaoFlight;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface IvaoClient {
    @GetExchange("/tracker/now/pilots")
    List<IvaoFlight> getFlights(@RequestHeader HttpHeaders headers);

    @GetExchange("/tracker/now/atc")
    List<IvaoAtc> getControllers(@RequestHeader HttpHeaders headers);

    @GetExchange("/aircrafts/{aircraftId}")
    AircraftDetailsResponse getAircraftDetails(@RequestHeader HttpHeaders headers, @PathVariable String aircraftId);
}
