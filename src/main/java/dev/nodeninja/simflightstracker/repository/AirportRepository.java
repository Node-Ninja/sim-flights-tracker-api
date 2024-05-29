package dev.nodeninja.simflightstracker.repository;

import dev.nodeninja.simflightstracker.model.Airport;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirportRepository extends MongoRepository<Airport, ObjectId> {
    Optional<Airport> findAirportByIdent(String ident);
}
