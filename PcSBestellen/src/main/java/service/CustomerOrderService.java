package service;

import entities.OrderState;
import entities.Product;
import entities.rest.CustomerOrder;
import entities.rest.CustomerProduct;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.CustomerOrderRepository;
import repository.ProductRepository;
import rest.service.RestService;
import rest.util.HateoasResponse;

/**
 * @author peaseloxes
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/customerorders")
@SuppressWarnings("squid:UnusedPrivateMethod")
public class CustomerOrderService extends RestService<CustomerOrder> {

    @Setter
    @Autowired
    private CustomerOrderRepository repository;

    @Setter
    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    @Override
    public void initRepository() {
        setRestRepository(repository);
        CustomerOrder testOrder = CustomerOrder.builder()
                .orderId("1")
                .customerId("1")
                .orderStatus(OrderState.RUNNING.toString())
                .deliveryStatus("NOT SCHEDULED")
                .totalPrice(0)
                .build();
        testOrder.setId("1");

        CustomerProduct testProduct1 = new CustomerProduct("1", "TEST", 1, 0.00);
        CustomerProduct testProduct2 = new CustomerProduct("2", "TEST", 2, 2.00);
        List<CustomerProduct> testList = new ArrayList<>();
        testList.add(testProduct1);
        testList.add(testProduct2);
        testOrder.setProducts(testList);
        repository.save(testOrder);
    }

    @Override
    public Class<? extends RestService<CustomerOrder>> getClazz() {
        return this.getClass();
    }

    @Override
    public HttpEntity<HateoasResponse> post(@RequestBody final CustomerOrder customerOrder) {
        if (customerOrder.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        HttpEntity<HateoasResponse> response = super.post(customerOrder);
        String custOrderId = ((CustomerOrder) (response.getBody().getContent())).getId();
        CustomerOrder order = repository.findOne(custOrderId);
        order.setOrderId(custOrderId);
        // TODO stock decrease
        return super.post(order);
    }

    @Override
    public HttpEntity<HateoasResponse> update(@PathVariable("id") String id, @RequestBody CustomerOrder customerOrder) {
        // TODO implement mongo update
        // TODO stock decrease
        return super.update(id, customerOrder);
    }

    private void stockDecrease(CustomerProduct customerProduct) {
        String id = customerProduct.getId();
        Product product = productRepository.getOne(id);
        int newStock = product.getStock() - customerProduct.getAmount();
        product.setStock(newStock);
        productRepository.save(product);
    }
}
