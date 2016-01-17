package service;

import entities.OrderState;
import entities.Product;
import entities.rest.CustomerOrder;
import entities.rest.CustomerProduct;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import peaseloxes.spring.annotations.LoginRequired;
import peaseloxes.spring.annotations.WrapWithLink;
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
    private CustomerOrderRepository customerOrderRepository;

    @Setter
    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    @Override
    public void initRepository() {
        setRestRepository(customerOrderRepository);
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
        customerOrderRepository.save(testOrder);
    }

    @Override
    public Class<? extends RestService<CustomerOrder>> getClazz() {
        return this.getClass();
    }

    @Override
    @WrapWithLink
    @LoginRequired
    public HttpEntity<HateoasResponse> post(@RequestBody final CustomerOrder customerOrder,
                                            final HttpServletRequest request) {
        if (customerOrder.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        HttpEntity<HateoasResponse> response = super.post(customerOrder, request);
        String custOrderId = ((CustomerOrder)(response.getBody().getContent())).getId();
        CustomerOrder order = customerOrderRepository.findOne(custOrderId);
        order.setOrderId(custOrderId);
        return super.post(order, request);
    }

    @Override
    @WrapWithLink
    @LoginRequired
    public HttpEntity<HateoasResponse> update(@PathVariable("id") String id,
                                              @RequestBody CustomerOrder customerOrder,
                                              final HttpServletRequest request) {
        if (customerOrder.getOrderStatus().equals(OrderState.PACKAGED.toString())) {
            customerOrder.getProducts().forEach(this::stockDecrease);
        }
        return super.update(id, customerOrder, request);
    }

    /**
     * Reduce stock for a customer product.
     *
     * @param customerProduct the customer product.
     */
    private void stockDecrease(CustomerProduct customerProduct) {
        Product product = productRepository.findOne(customerProduct.getId());
        product.setStock(product.getStock() - customerProduct.getAmount());
        productRepository.save(product);
    }
}
