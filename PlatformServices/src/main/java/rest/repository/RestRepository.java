package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by alex on 1/5/16.
 * @param <T> the abstract entity for this service.
 */
@Repository
public interface RestRepository<T> extends JpaRepository<T, String> {
}
