package service;

import auth.repository.TokenRepository;
import auth.repository.UserRepository;
import entities.Address;
import entities.Customer;
import entities.auth.Token;
import entities.auth.User;
import entities.rest.CustomerOrder;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import peaseloxes.spring.annotations.DataVaultObservable;
import peaseloxes.spring.annotations.LoginRequired;
import peaseloxes.spring.annotations.WrapWithLink;
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

    @Autowired
    @Setter
    private TokenRepository tokenRepository;

    @Autowired
    @Setter
    private UserRepository userRepository;

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

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    @WrapWithLink
    @LoginRequired
    public HttpEntity<HateoasResponse> getCustomer(final HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Token t = tokenRepository.findByToken(token);
        return HateoasUtil.build(repository.findOne(t.getCustId()));
    }

    @LoginRequired
    @WrapWithLink
    @DataVaultObservable
    @Override
    public HttpEntity<HateoasResponse> post(@RequestBody final Customer customer, final HttpServletRequest request) {
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

        Customer savedCustomer = repository.save(customer);
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Token t = tokenRepository.findByToken(token);
        t.setCustId(savedCustomer.getId());
        tokenRepository.save(t);

        User user = userRepository.findOne(t.getUserId());
        user.setCustomerId(savedCustomer.getId());
        userRepository.save(user);

        HateoasResponse response = HateoasUtil.toHateoas(
                customer,
                WrapWithLink.Type.SELF.link(request, "/" + customer.getId()),
                WrapWithLink.Type.POST.link(request, ""),
                WrapWithLink.Type.UPDATE.link(request, "/" + customer.getId()),
                WrapWithLink.Type.DELETE.link(request, "/" + customer.getId())
        );
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /**
     * Retrieve all orders given a customer id.
     *
     * @param id      the customer id.
     * @param request the Servlet Request.
     * @return a list of orders.
     */
    @WrapWithLink
    @RequestMapping(value = "/{id}/orders", method = RequestMethod.GET)
    public HttpEntity<HateoasResponse> getByCustomer(@PathVariable("id") String id, final HttpServletRequest request) {
        List<CustomerOrder> list = customerOrderRepository.findByCustomerId(id);
        return HateoasUtil.build(list);
    }

    @Override
    public Class<? extends RestService<Customer>> getClazz() {
        return CustomerService.class;
    }
}
