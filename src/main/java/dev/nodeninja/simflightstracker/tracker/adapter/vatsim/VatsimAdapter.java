package dev.nodeninja.simflightstracker.tracker.adapter.vatsim;

import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.VatsimDataApiResponse;
import dev.nodeninja.simflightstracker.api.v2.model.VatsimEvent;
import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.VatsimV3BaseResponse;
import dev.nodeninja.simflightstracker.api.v2.model.VatsimTransceiver;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

public interface VatsimAdapter {
    VatsimDataApiResponse liveData() throws HttpClientErrorException;

    String metarByIcaoId(String icaoId) throws HttpClientErrorException;

    VatsimV3BaseResponse<List<VatsimEvent>> allEvents() throws HttpClientErrorException;

    VatsimV3BaseResponse<VatsimEvent> eventDetails(String eventId) throws HttpClientErrorException;

    List<VatsimTransceiver> allTransceivers() throws HttpClientErrorException;
}
