package repository;

import entities.Customer;
import org.springframework.stereotype.Component;
import rest.repository.RestRepository;

/**
 * Created by alex on 1/11/16.
 */
@Component
public interface CustomerRepository extends RestRepository<Customer> {
}
