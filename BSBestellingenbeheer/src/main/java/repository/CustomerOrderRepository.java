package repository;

import entities.rest.CustomerOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rest.repository.RestRepository;

/**
 * @author peaseloxes
 */
@Repository
public interface CustomerOrderRepository extends MongoRepository<CustomerOrder, String>, RestRepository<CustomerOrder> {
}
