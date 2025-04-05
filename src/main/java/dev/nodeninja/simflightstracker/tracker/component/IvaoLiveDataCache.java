package dev.nodeninja.simflightstracker.tracker.component;

import dev.nodeninja.simflightstracker.tracker.adapter.ivao.model.IvaoAtc;
import dev.nodeninja.simflightstracker.tracker.adapter.ivao.model.IvaoFlight;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Getter
public class IvaoLiveDataCache {
    private volatile List<IvaoFlight> ivaoFlights;
    private volatile List<IvaoAtc> ivaoControllers;

    public void updateIvaoFlightsCache(List<IvaoFlight> ivaoFlights) {
        this.ivaoFlights = ivaoFlights;
    }

    public void updateIvaoControllersCache(List<IvaoAtc> ivaoControllers) {
        this.ivaoControllers = ivaoControllers;
    }
}
