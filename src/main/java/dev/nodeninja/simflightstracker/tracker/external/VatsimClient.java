package dev.nodeninja.simflightstracker.tracker.external;

import dev.nodeninja.simflightstracker.api.v2.model.VatsimATCHistory;
import dev.nodeninja.simflightstracker.api.v2.model.VatsimEvent;
import dev.nodeninja.simflightstracker.api.v2.model.VatsimTransceiver;
import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.*;
import dev.nodeninja.simflightstracker.tracker.model.VatsimTokenResponse;
import dev.nodeninja.simflightstracker.tracker.model.VatsimUserDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

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

    @GetExchange
    VatsimUserHours getUserHours(URI uri);

    @PostExchange(contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    VatsimTokenResponse exchangeCodeForToken(URI uri, @RequestBody MultiValueMap<String, String> body);

    @GetExchange
    VatsimUserDetails getUserDetails(URI uri, @RequestHeader HttpHeaders headers);

    @GetExchange
    VatsimATCHistory getATCHistory(URI uri);
}
