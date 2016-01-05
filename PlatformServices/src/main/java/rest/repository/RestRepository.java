package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 1/5/16.
 */
@Component
public interface RestRepository<T> extends JpaRepository<T, String>{
}
