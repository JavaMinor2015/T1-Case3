package service;

import entities.Address;
import entities.Customer;
import entities.rest.CustomerOrder;
import global.Globals;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import repository.AddressRepository;
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
    @Setter
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    @Setter
    private AddressRepository addressRepository;

    @Autowired
    @Setter
    private CustomerRepository repository;

    @PostConstruct
    @Override
    public void initRepository() {
        setRestRepository(repository);
        Customer customer = new Customer();
        customer.setFirstName("First");
        customer.setInitials("O.R.");
        customer.setLastName("Last");


        Address sample = new Address();
        sample.setId("1");
        sample.setZipcode("1234 AB");
        sample.setStreetname("Elm");
        sample.setNumber("5A");
        sample.setCity("Sin");
        addressRepository.save(sample);

        customer.setAddress(sample);
        customer.setDeliveryAddress(sample);
        repository.save(customer);
    }

    @Override
    public HttpEntity<HateoasResponse> post(@RequestBody final Customer customer) {
        Address existingAddress = addressRepository.findByZipcodeAndNumber(
                customer.getAddress().getZipcode(),
                customer.getAddress().getNumber()
        );

        if (existingAddress == null) {
            addressRepository.save(customer.getAddress());
        } else {
            customer.setAddress(existingAddress);
        }

        Address existingDeliveryAddress = addressRepository.findByZipcodeAndNumber(
                customer.getDeliveryAddress().getZipcode(),
                customer.getDeliveryAddress().getNumber()
        );

        if (existingDeliveryAddress == null) {
            addressRepository.save(customer.getDeliveryAddress());
        } else {
            customer.setDeliveryAddress(existingDeliveryAddress);
        }

        repository.save(customer);
        return HateoasUtil.build(
                customer,
                HateoasUtil.makeLink(getClazz(), Globals.SELF, customer.getId()),
                HateoasUtil.makeLink(getClazz(), Globals.NEXT, customer.getId()),
                HateoasUtil.makeLink(getClazz(), Globals.PREV, customer.getId()),
                HateoasUtil.makeLink(getClazz(), Globals.UPDATE, customer.getId()),
                HateoasUtil.makeLink(getClazz(), Globals.DELETE, customer.getId())
        );
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
