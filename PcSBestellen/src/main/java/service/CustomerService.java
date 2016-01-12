package service;

import entities.Customer;
import entities.rest.CustomerOrder;
import global.Globals;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import repository.CustomerOrderRepository;
import repository.CustomerRepository;
import rest.service.RestService;
import rest.util.HateoasResponse;
import rest.util.HateoasUtil;

/**
 * Created by alex on 1/11/16.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/customers")
public class CustomerService extends RestService<Customer> {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

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

    /**
     * Retrieve all orders given a customer id.
     *
     * @param id the customer id.
     * @return a list of orders.
     */
    @RequestMapping(value = "/{id}/orders", method = RequestMethod.GET)
    public HttpEntity<HateoasResponse> getByCustomer(@PathVariable("id") String id) {
        List<CustomerOrder> list = customerOrderRepository.findByCustomerId(id);
        return HateoasUtil.build(
                list,
                HateoasUtil.makeLink(getClazz(), Globals.SELF, id),
                HateoasUtil.makeLink(getClazz(), Globals.NEXT, id),
                HateoasUtil.makeLink(getClazz(), Globals.PREV, id),
                HateoasUtil.makeLink(getClazz(), Globals.UPDATE, id),
                HateoasUtil.makeLink(getClazz(), Globals.DELETE, id)
        );
    }

    @Override
    public Class<? extends RestService<Customer>> getClazz() {
        return CustomerService.class;
    }
}
