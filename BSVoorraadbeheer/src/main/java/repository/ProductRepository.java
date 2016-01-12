package repository;

import entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rest.repository.RestRepository;

/**
 * Created by alex on 1/6/16.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, String>, RestRepository<Product> {
}
