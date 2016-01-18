package rest.repository;

import entities.abs.PersistenceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by alex on 1/5/16.
 *
 * @param <T> the abstract entity for this service.
 */
@Repository
public interface RestRepository<T extends PersistenceEntity> extends CrudRepository<T, String> {
}
