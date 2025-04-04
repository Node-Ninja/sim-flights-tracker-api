package dev.nodeninja.simflightstracker.tracker.schedule;

import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.VatsimDataApiResponse;
import dev.nodeninja.simflightstracker.tracker.component.VatsimLiveDataCache;
import dev.nodeninja.simflightstracker.tracker.external.VatsimClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SftSchedules {
    private final VatsimLiveDataCache vatsimLiveDataCache;
    private final VatsimClient vatsimClient;

    @Scheduled(fixedRateString = "${sft.timings.vatsim-data-delay}")
    public void refreshVatsimLiveData() {
        try {
            VatsimDataApiResponse vatsimLiveData = vatsimClient.getLiveData();

            //  update cache with refreshed live data;
            vatsimLiveDataCache.updateCache(vatsimLiveData);
        } catch (Exception e) {
            // Do nothing;
        }
    }
}
