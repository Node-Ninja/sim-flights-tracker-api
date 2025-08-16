package dev.nodeninja.simflightstracker.tracker.schedule;

import dev.nodeninja.simflightstracker.api.v2.model.LatLng;
import dev.nodeninja.simflightstracker.api.v2.model.VatsimTransceiver;
import dev.nodeninja.simflightstracker.tracker.adapter.ivao.model.IvaoFlight;
import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.VatsimDataApiResponse;
import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.VatsimFlight;
import dev.nodeninja.simflightstracker.tracker.component.IvaoLiveDataCache;
import dev.nodeninja.simflightstracker.tracker.component.VatsimLiveDataCache;
import dev.nodeninja.simflightstracker.tracker.external.IvaoClient;
import dev.nodeninja.simflightstracker.tracker.external.VatsimClient;
import dev.nodeninja.simflightstracker.tracker.http.client.AuthenticatedRestClient;
import dev.nodeninja.simflightstracker.tracker.http.model.IDProvider;
import dev.nodeninja.simflightstracker.tracker.service.impl.TrackUpdaterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class SftSchedules {

    private final VatsimLiveDataCache vatsimLiveDataCache;
    private final IvaoLiveDataCache ivaoLiveDataCache;
    private final VatsimClient vatsimClient;
    private final IvaoClient ivaoClient;
    private final AuthenticatedRestClient authenticatedRestClient;
    private final TrackUpdaterService trackUpdaterService;

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

    @Scheduled(fixedRateString = "PT22S", initialDelayString = "PT10S")
    public void generatePaths() {
        var vatsimFlights = vatsimLiveDataCache.getVatsimLiveData().getPilots();
        var ivaoFlights = ivaoLiveDataCache.getIvaoFlights();

        if (!vatsimFlights.isEmpty()) {
            Map<String, LatLng> updateMap = new HashMap<>();
            List<String> activeCallsigns = new ArrayList<>();

            for (VatsimFlight flight : vatsimFlights) {
                String callsign = flight.getCallsign();
                activeCallsigns.add(callsign);

                LatLng point = LatLng.builder()
                        .latitude(flight.getLatitude())
                        .longitude(flight.getLongitude())
                        .altitude(flight.getAltitude())
                        .speed(flight.getGroundspeed())
                        .timestamp(System.currentTimeMillis())
                        .build();

                updateMap.put(callsign, point);
            }

            trackUpdaterService.updateTracks(updateMap, "vatsim");
            trackUpdaterService.removeStaleTracks(activeCallsigns, "vatsim");
        }

        if (!ivaoFlights.isEmpty()) {
            Map<String, LatLng> updateMap = new HashMap<>();
            List<String> activeCallsigns = new ArrayList<>();

            for (IvaoFlight flight : ivaoFlights) {
                var hasTrack = flight.getLastTrack() != null;

                if (hasTrack) {

                    String callsign = flight.getCallsign();
                    activeCallsigns.add(callsign);

                    LatLng point = LatLng.builder()
                            .latitude(flight.getLastTrack().getLatitude())
                            .longitude(flight.getLastTrack().getLongitude())
                            .altitude(flight.getLastTrack().getAltitude())
                            .speed(flight.getLastTrack().getGroundSpeed())
                            .timestamp(System.currentTimeMillis())
                            .build();

                    updateMap.put(callsign, point);
                }
            }

            trackUpdaterService.updateTracks(updateMap, "ivao");
            trackUpdaterService.removeStaleTracks(activeCallsigns, "ivao");
        }
    }
}
