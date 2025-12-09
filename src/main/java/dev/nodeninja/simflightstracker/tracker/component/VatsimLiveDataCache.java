package dev.nodeninja.simflightstracker.tracker.component;

import dev.nodeninja.simflightstracker.api.v2.model.VatsimTransceiver;
import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.VatsimDataApiResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Getter
public class VatsimLiveDataCache {

    private volatile VatsimDataApiResponse vatsimLiveData;
    private volatile List<VatsimTransceiver> vatsimTransceivers;

    public void updateCache(VatsimDataApiResponse vatsimLiveData, List<VatsimTransceiver> vatsimTransceivers) {
        this.vatsimLiveData = vatsimLiveData;
        this.vatsimTransceivers = vatsimTransceivers;
    }
}
