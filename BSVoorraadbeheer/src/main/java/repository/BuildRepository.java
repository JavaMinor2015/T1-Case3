package repository;

import entity.BuildStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import rest.repository.RestRepository;

/**
 * Created by alex on 1/12/16.
 */
public interface BuildRepository extends MongoRepository<BuildStatus, String>, RestRepository<BuildStatus> {
}
