package service;

import entities.Product;
import entities.rest.CustomerOrder;
import entities.rest.CustomerProduct;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
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

    @Autowired
    private CustomerOrderRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    @Override
    public void initRepository() {
        setRestRepository(repository);
        CustomerOrder testOrder = CustomerOrder.builder()
                .orderId("1")
                .customerId("1")
                .orderStatus("OPEN")
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
    public HttpEntity<HateoasResponse> update(@PathVariable("id") String id, @RequestBody CustomerOrder customerOrder) {
        // TODO implement mongo update
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
