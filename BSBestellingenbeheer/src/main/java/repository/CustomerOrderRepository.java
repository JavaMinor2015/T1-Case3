package repository;

import entities.rest.CustomerOrder;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rest.repository.RestRepository;

/**
 * @author peaseloxes
 */
@Repository
public interface CustomerOrderRepository extends MongoRepository<CustomerOrder, String>, RestRepository<CustomerOrder> {
    /**
     * Find orders by customer id.
     *
     * @param id the customer id.
     * @return orders made by customer with provided id.
     */
    List<CustomerOrder> findByCustomerId(final String id);
}
