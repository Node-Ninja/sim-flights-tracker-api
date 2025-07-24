package dev.nodeninja.simflightstracker.tracker.service;

import dev.nodeninja.simflightstracker.api.v2.model.FlightTrack;
import dev.nodeninja.simflightstracker.api.v2.model.LatLng;
import dev.nodeninja.simflightstracker.api.v2.model.SftFlightTrack;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TrackUpdaterService {

    private final MongoTemplate mongoTemplate;

    public void updateTracks(Map<String, LatLng> flightData, String network) {
        List<FlightTrack> tracksToInsert = new ArrayList<>();

        for (Map.Entry<String, LatLng> entry : flightData.entrySet()) {
            String callsign = entry.getKey();
            LatLng point = entry.getValue();

            FlightTrack track = new FlightTrack();
            track.setCallsign(callsign);
            track.setLatitude(point.getLatitude());
            track.setLongitude(point.getLongitude());
            track.setAltitude(point.getAltitude());
            track.setTimestamp(point.getTimestamp());
            track.setSpeed(point.getSpeed());

            tracksToInsert.add(track);
        }

        mongoTemplate.insert(tracksToInsert, "flight_tracks_" + network);
    }

    public void removeStaleTracks(List<String> activeCallsigns, String network) {
        Query staleQuery = new Query(Criteria.where("callsign").nin(activeCallsigns));
        mongoTemplate.remove(staleQuery, FlightTrack.class, "flight_tracks_" + network);
    }

    public SftFlightTrack getTrack(String callsign, String network) {
        String collectionName = "flight_tracks_" + network;

        Query query = new Query(Criteria.where("callsign").is(callsign));

        var tracks = mongoTemplate.find(query, FlightTrack.class, collectionName);

        return SftFlightTrack.builder()
                .callsign(callsign)
                .points(tracks)
                .build();
    }
}
