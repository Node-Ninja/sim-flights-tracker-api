package dev.nodeninja.simflightstracker.tracker.external;

import dev.nodeninja.simflightstracker.api.v2.model.VatsimEvent;
import dev.nodeninja.simflightstracker.api.v2.model.VatsimTransceiver;
import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.FlightPlanHistoryItem;
import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.VatsimDataApiResponse;
import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.VatsimFlightsHistory;
import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.VatsimV3BaseResponse;
import org.springframework.web.service.annotation.GetExchange;

import java.net.URI;
import java.util.List;

public interface VatsimClient {
    @GetExchange("/vatsim-data.json")
    VatsimDataApiResponse getLiveData();

    @GetExchange("/transceivers-data.json")
    List<VatsimTransceiver> getAllTransceivers();

    @GetExchange()
    String metarByIcaoId(URI uri);

    @GetExchange
    VatsimV3BaseResponse<List<VatsimEvent>> getAllEvents(URI uri);

    @GetExchange
    VatsimV3BaseResponse<VatsimEvent> eventDetails(URI uri);

    @GetExchange
    VatsimFlightsHistory getFlightsHistory(URI uri);

    @GetExchange
    List<FlightPlanHistoryItem> flightPlanHistory(URI uri);
}
