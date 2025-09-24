package dev.nodeninja.simflightstracker.api.v2.controller;

import dev.nodeninja.simflightstracker.api.v2.model.LiveDataStats;
import dev.nodeninja.simflightstracker.tracker.component.IvaoLiveDataCache;
import dev.nodeninja.simflightstracker.tracker.component.VatsimLiveDataCache;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/stats")
public class GeneralController {
    private final VatsimLiveDataCache vatsimLiveDataCache;
    private final IvaoLiveDataCache ivaoLiveDataCache;

    @GetMapping
    public LiveDataStats getStats() {
        var vatsimLiveData = vatsimLiveDataCache.getVatsimLiveData();
        var ivaoFlights = ivaoLiveDataCache.getIvaoFlights();
        var ivaoControllers = ivaoLiveDataCache.getIvaoControllers();

        return LiveDataStats.builder()
                .totalVatsimFlights(vatsimLiveData.getPilots().size())
                .totalVatsimControllers(vatsimLiveData.getControllers().size())
                .totalIvaoFlights(ivaoFlights.size())
                .totalIvaoControllers(ivaoControllers.size())
                .build();
    }
}
