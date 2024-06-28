package dev.nodeninja.simflightstracker.tracker.adapter.ivao;

import dev.nodeninja.simflightstracker.tracker.adapter.ivao.model.AircraftDetailsResponse;
import dev.nodeninja.simflightstracker.tracker.adapter.ivao.model.IvaoAtc;
import dev.nodeninja.simflightstracker.tracker.adapter.ivao.model.IvaoFlight;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

public interface IvaoAdapter {
    List<IvaoFlight> flights() throws HttpClientErrorException;
    List<IvaoAtc> controllers() throws HttpClientErrorException;
    AircraftDetailsResponse aircraftDetails(String aircraftId) throws HttpClientErrorException;
}
