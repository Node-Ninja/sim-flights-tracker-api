package dev.nodeninja.simflightstracker.tracker.component;

import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.VatsimDataApiResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class VatsimLiveDataCache {

    private volatile VatsimDataApiResponse vatsimLiveData;

    public void updateCache(VatsimDataApiResponse vatsimLiveData) {
        this.vatsimLiveData = vatsimLiveData;
    }
}
