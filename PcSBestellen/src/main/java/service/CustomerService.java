package service;

import entities.Customer;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.CustomerRepository;
import rest.service.RestService;

/**
 * Created by alex on 1/11/16.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/customers")
public class CustomerService extends RestService<Customer> {

    @Autowired
    private CustomerRepository repository;

    @PostConstruct
    @Override
    public void initRepository() {
        setRestRepository(repository);
        Customer customer = new Customer();
        customer.setFirstName("First");
        customer.setInitials("O.R.");
        customer.setLastName("Last");
        customer.setAddress(null);
        customer.setDeliveryAddress(null);
        repository.save(customer);
    }

    @Override
    public Class<? extends RestService<Customer>> getClazz() {
        return CustomerService.class;
    }
}
