package dev.nodeninja.simflightstracker.tracker.schedule;

import dev.nodeninja.simflightstracker.api.v2.model.VatsimTransceiver;
import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.VatsimDataApiResponse;
import dev.nodeninja.simflightstracker.tracker.component.IvaoLiveDataCache;
import dev.nodeninja.simflightstracker.tracker.component.VatsimLiveDataCache;
import dev.nodeninja.simflightstracker.tracker.external.IvaoClient;
import dev.nodeninja.simflightstracker.tracker.external.VatsimClient;
import dev.nodeninja.simflightstracker.tracker.http.client.AuthenticatedRestClient;
import dev.nodeninja.simflightstracker.tracker.http.model.IDProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SftSchedules {

    private final VatsimLiveDataCache vatsimLiveDataCache;
    private final IvaoLiveDataCache ivaoLiveDataCache;
    private final VatsimClient vatsimClient;
    private final IvaoClient ivaoClient;
    private final AuthenticatedRestClient authenticatedRestClient;

    @Scheduled(fixedRateString = "${sft.timings.vatsim-data-delay}")
    public void refreshVatsimLiveData() {
        try {
            VatsimDataApiResponse vatsimLiveData = vatsimClient.getLiveData();
            List<VatsimTransceiver> vatsimTransceivers = vatsimClient.getAllTransceivers();

            vatsimLiveDataCache.updateCache(vatsimLiveData, vatsimTransceivers);
        } catch (Exception e) {
            log.error("Could not refresh vatsim live data :: {}", e.getMessage());
        }
    }

    @Scheduled(fixedRateString = "${sft.timings.ivao-data-delay}")
    public void refreshIvaoLiveData() {
        try {
            authenticatedRestClient.requestWithRetry(IDProvider.IVAO, httpHeaders -> {
               var flights = ivaoClient.getFlights(httpHeaders);
               var controllers = ivaoClient.getControllers(httpHeaders);

                ivaoLiveDataCache.updateIvaoFlightsCache(flights);
                ivaoLiveDataCache.updateIvaoControllersCache(controllers);

                return true;
            });
        } catch (Exception e) {
            log.error("Could not refresh ivao live data :: {}", e.getMessage());
        }
    }
}
