package service;

import entities.rest.CustomerOrder;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.CustomerOrderRepository;
import rest.service.RestService;
import rest.util.HateoasResponse;
import rest.util.HateoasUtil;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author peaseloxes
 */
@RestController
@RequestMapping(value = "/customerorders")
public class CustomerOrderService extends RestService<CustomerOrder> {

    @Autowired
    private CustomerOrderRepository repository;

    @PostConstruct
    @Override
    public void initRepository() {
        setRestRepository(repository);
        CustomerOrder testOrder = CustomerOrder.builder()
                .orderId("1")
                .customerId("1234")
                .orderStatus("OPEN")
                .deliveryStatus("NOT SCHEDULED")
                .totalPrice(0)
                .build();
        testOrder.setId("1");
        repository.save(testOrder);
    }

    @Override
    public Class<? extends RestService<CustomerOrder>> getClazz() {
        return this.getClass();
    }

    @Override
    public HttpEntity<HateoasResponse> post(@RequestBody CustomerOrder customerOrder) {
        // TODO implement mongo post
        return super.post(customerOrder);
    }

    @Override
    public HttpEntity<HateoasResponse> update(@PathVariable("id") String id, @RequestBody CustomerOrder customerOrder) {
        // TODO implement mongo update
        return super.update(id, customerOrder);
    }

    @Override
    public HttpEntity<HateoasResponse> delete(@PathVariable("id") String id) {
        // TODO implement mongo delete
        return super.delete(id);
    }
}
