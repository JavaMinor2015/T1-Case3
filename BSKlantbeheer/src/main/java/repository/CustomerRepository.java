package repository;

import entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rest.repository.RestRepository;

/**
 * Created by alex on 1/11/16.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String>, RestRepository<Customer> {
}
