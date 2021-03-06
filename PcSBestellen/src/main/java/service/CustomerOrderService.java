package service;

import auth.repository.TokenRepository;
import entities.OrderState;
import entities.Product;
import entities.auth.Token;
import entities.rest.CustomerOrder;
import entities.rest.CustomerProduct;
import java.time.Instant;
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
import peaseloxes.spring.annotations.DataVaultObservable;
import peaseloxes.spring.annotations.LoginRequired;
import peaseloxes.spring.annotations.WrapWithLink;
import repository.CustomerOrderRepository;
import repository.ProductRepository;
import rest.service.RestService;
import rest.util.HateoasResponse;
import rest.util.HateoasUtil;

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

    @Setter
    @Autowired
    private TokenRepository tokenRepository;

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

    /**
     * Retrieve a customer's orders.
     *
     * @param request the http request.
     * @return a customers orders.
     */
    @RequestMapping(value = "/myorders", method = RequestMethod.GET)
    @WrapWithLink
    @LoginRequired
    public HttpEntity<HateoasResponse> getMyOrders(final HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Token t = tokenRepository.findByToken(token);
        return HateoasUtil.build(customerOrderRepository.findByCustomerId(t.getCustId()));
    }

    @Override
    @WrapWithLink
    @DataVaultObservable
    @LoginRequired
    public HttpEntity<HateoasResponse> post(@RequestBody final CustomerOrder customerOrder,
                                            final HttpServletRequest request) {
        if (customerOrder.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Token t = tokenRepository.findByToken(token);
        customerOrder.setCustomerId(t.getCustId());
        HttpEntity<HateoasResponse> response = super.post(customerOrder, request);
        String custOrderId = ((CustomerOrder) (response.getBody().getContent())).getId();
        CustomerOrder order = customerOrderRepository.findOne(custOrderId);
        order.setOrderId(custOrderId);
        order.setTimestamp(Instant.now().toEpochMilli());
        return super.post(order, request);
    }

    @Override
    @WrapWithLink
    @LoginRequired
    public HttpEntity<HateoasResponse> update(@PathVariable("id") String id,
                                              @RequestBody CustomerOrder customerOrder,
                                              final HttpServletRequest request) {
        if (customerOrder.getId() == null || id == null) {
            return new ResponseEntity<>(new HateoasResponse("Invalid input data for update."), HttpStatus.BAD_REQUEST);
        }
        if (!checkStock(customerOrder)) {
            return new ResponseEntity<>(new HateoasResponse("Order amount exceeds available stock"), HttpStatus.BAD_REQUEST);
        }
        if (customerOrder.getOrderStatus().equals(OrderState.PACKAGED.toString())) {
            customerOrder.getProducts().forEach(this::stockDecrease);
        }
        return super.update(id, customerOrder, request);
    }

    /**
     * Check the available stock.
     *
     * @param customerOrder the customer order.
     * @return true if valid order amount, false otherwise.
     */
    private boolean checkStock(final CustomerOrder customerOrder) {
        if (customerOrder.getOrderStatus().equals(OrderState.RUNNING.toString())) {
            for (CustomerProduct customerProduct : customerOrder.getProducts()) {
                final Product product = productRepository.findOne(customerProduct.getId());
                if (customerProduct.getAmount() > product.getStock()) {
                    return false;
                }
            }
        }
        return true;
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
