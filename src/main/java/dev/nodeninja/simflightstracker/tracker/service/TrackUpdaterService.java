package dev.nodeninja.simflightstracker.tracker.service;

import dev.nodeninja.simflightstracker.api.v2.model.FlightTrack;
import dev.nodeninja.simflightstracker.api.v2.model.LatLng;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TrackUpdaterService {

    private final MongoTemplate mongoTemplate;

    public void updateTracks(Map<String, LatLng> flightData, String network) {
        BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, FlightTrack.class, "flight_tracks_" + network);

        for (Map.Entry<String, LatLng> entry: flightData.entrySet()) {
            String callsign = entry.getKey();
            LatLng newPoint = entry.getValue();

            Query query = new Query(Criteria.where("callsign").is(callsign));
            Update update = new Update().push("points", newPoint);

            bulkOps.upsert(query, update);
        }

        bulkOps.execute();
    }

    public void removeStaleTracks(List<String> activeCallsigns, String network) {
        Query staleQuery = new Query(Criteria.where("callsign").nin(activeCallsigns));
        mongoTemplate.remove(staleQuery, FlightTrack.class, "flight_tracks_" + network);
    }

    public FlightTrack getTrack(String callsign, String network) {
        String collectionName = "flight_tracks_" + network;

        Query query = new Query(Criteria.where("callsign").is(callsign));

        return mongoTemplate.findOne(query, FlightTrack.class, collectionName);
    }
}
