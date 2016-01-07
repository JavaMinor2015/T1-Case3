package repository;

import entities.Product;
import org.springframework.stereotype.Component;
import rest.repository.RestRepository;

/**
 * Created by alex on 1/6/16.
 */
@Component
public interface ProductRepository extends RestRepository<Product> {
}
