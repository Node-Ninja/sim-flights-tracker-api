package dev.nodeninja.simflightstracker.tracker.repository;

import dev.nodeninja.simflightstracker.tracker.model.AuthRecord;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends MongoRepository<AuthRecord, ObjectId> {
    AuthRecord findByAuthId(String authId);
}
