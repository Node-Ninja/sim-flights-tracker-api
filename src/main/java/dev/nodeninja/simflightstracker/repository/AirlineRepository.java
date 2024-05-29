package dev.nodeninja.simflightstracker.repository;

import dev.nodeninja.simflightstracker.model.Airline;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirlineRepository extends MongoRepository<Airline, ObjectId> {
    Optional<Airline> findAirlineByIcao(String icao);
}
