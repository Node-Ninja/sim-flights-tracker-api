package dev.nodeninja.simflightstracker.tracker.repository;

import dev.nodeninja.simflightstracker.api.v2.model.FlightTrack;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightTracksRepository extends MongoRepository<FlightTrack, ObjectId> {
    @Query("{ 'callsign' : { $regex: ?0, $options: 'i' } }")
    FlightTrack findByCallsign(String callsign);
}
