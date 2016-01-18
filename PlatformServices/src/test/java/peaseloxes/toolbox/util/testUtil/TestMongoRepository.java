package peaseloxes.toolbox.util.testUtil;

import org.springframework.data.mongodb.repository.MongoRepository;
import rest.repository.RestRepository;

/**
 * Created by alex on 1/18/16.
 */
public interface TestMongoRepository extends MongoRepository<TestObject, String>, RestRepository<TestObject> {
}
