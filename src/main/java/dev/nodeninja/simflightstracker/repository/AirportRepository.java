package dev.nodeninja.simflightstracker.repository;

import dev.nodeninja.simflightstracker.model.Airport;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirportRepository extends MongoRepository<Airport, ObjectId> {
    Optional<Airport> findAirportByIdent(String ident);

    @Query("{$or: [{ ident: ?0}, {iata_code: ?0}, {name: {'$regex': ?0, $options: 'i'}}]}")
    List<Airport> findBy(String query);
}
