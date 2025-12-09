package dev.nodeninja.simflightstracker.tracker.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import dev.nodeninja.simflightstracker.api.v2.model.Airline;

import java.util.Optional;

@Repository
public interface AirlineRepository extends MongoRepository<Airline, ObjectId> {
    Optional<Airline> findAirlineByIcao(String icao);
}
