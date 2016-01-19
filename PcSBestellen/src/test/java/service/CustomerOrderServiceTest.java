package service;

import entities.OrderState;
import entities.Product;
import entities.rest.CustomerOrder;
import entities.rest.CustomerProduct;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import repository.CustomerOrderRepository;
import repository.ProductRepository;
import rest.util.HateoasResponse;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;

/**
 * Created by alex on 1/14/16.
 */
public class CustomerOrderServiceTest {

    private CustomerOrderService customerOrderService;
    private CustomerOrderRepository mockCustomerOrderRepository;
    private ProductRepository mockProductRepository;
    private HttpServletRequest mockRequest;

    @Before
    public void setUp() {
        customerOrderService = new CustomerOrderService();
        mockCustomerOrderRepository = Mockito.mock(CustomerOrderRepository.class);
        mockProductRepository = Mockito.mock(ProductRepository.class);

        mockRequest = Mockito.mock(HttpServletRequest.class);
        customerOrderService.setCustomerOrderRepository(mockCustomerOrderRepository);
        customerOrderService.setProductRepository(mockProductRepository);
        customerOrderService.setRestRepository(mockCustomerOrderRepository);
    }

    @Test
    public void testInitRepository() throws Exception {
        Mockito.when(mockCustomerOrderRepository.save(any(CustomerOrder.class))).thenReturn(new CustomerOrder());
        customerOrderService.initRepository();
    }

    @Test
    public void testGetClazz() throws Exception {
        assertThat(customerOrderService.getClazz().equals(CustomerOrderService.class), is(true));
    }

    @Test
    public void testPost() throws Exception {
        Mockito.when(mockProductRepository.findOne(any(String.class))).thenReturn(new Product());
        Mockito.when(mockProductRepository.save(any(Product.class))).thenReturn(new Product());
        CustomerOrder test = new CustomerOrder();
        test.setId(null);
        Mockito.when(mockCustomerOrderRepository.save(any(CustomerOrder.class))).thenReturn(test);
        Mockito.when(mockCustomerOrderRepository.findOne(any(String.class))).thenReturn(test);
        assertThat(customerOrderService.post(test, mockRequest).getBody().getContent(), is(test));
        test.setId("1");
        assertThat(((ResponseEntity) customerOrderService.post(test, mockRequest)).getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void testUpdate() throws Exception {
        Product p = new Product();
        p.setStock(100);
        Mockito.when(mockProductRepository.findOne(any(String.class))).thenReturn(p);
        Mockito.when(mockProductRepository.save(any(Product.class))).thenReturn(p);
        CustomerOrder test = new CustomerOrder();
        test.setId("1");
        Mockito.when(mockCustomerOrderRepository.save(any(CustomerOrder.class))).thenReturn(test);
        Mockito.when(mockCustomerOrderRepository.findOne(any(String.class))).thenReturn(test);

        test.setId(null);
        HttpEntity<HateoasResponse> response = customerOrderService.update("1", test, mockRequest);
        assertThat(((ResponseEntity)response).getStatusCode(), is(HttpStatus.BAD_REQUEST));

        test.setId("1");
        response = customerOrderService.update(null, test, mockRequest);
        assertThat(((ResponseEntity)response).getStatusCode(), is(HttpStatus.BAD_REQUEST));

        test.setId("1");
        test.setOrderStatus(OrderState.PACKAGED.toString());
        response = customerOrderService.update("1", test, mockRequest);
        assertThat(response.getBody().getContent(), is(test));

        test.setOrderStatus(OrderState.SHIPPED.toString());
        response = customerOrderService.update("1", test, mockRequest);
        assertThat(response.getBody().getContent(), is(test));

        test.setOrderStatus(OrderState.RUNNING.toString());
        test.getProducts().add(new CustomerProduct());
        test.getProducts().add(new CustomerProduct());
        test.getProducts().get(1).setAmount(Integer.MAX_VALUE);
        response = customerOrderService.update("1", test, mockRequest);
        assertThat(((ResponseEntity)response).getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void testStockDecrease() throws Exception {
        final Method stockDecrease = CustomerOrderService.class.getDeclaredMethod("stockDecrease", CustomerProduct.class);
        assertThat(stockDecrease.isAccessible(), is(false));
        stockDecrease.setAccessible(true);
        CustomerProduct product = new CustomerProduct();
        product.setAmount(1);
        Product productInStock = new Product();
        productInStock.setId("1");
        productInStock.setStock(5);
        Mockito.when(mockProductRepository.findOne(any(String.class))).thenReturn(productInStock);
        Mockito.when(mockProductRepository.save(any(Product.class))).thenReturn(productInStock);
        stockDecrease.invoke(customerOrderService, product);
        assertThat(productInStock.getStock(), is(4));
    }
}