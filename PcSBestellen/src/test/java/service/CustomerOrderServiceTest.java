package service;

import entities.rest.CustomerOrder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import repository.CustomerOrderRepository;
import repository.ProductRepository;
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

    @Before
    public void setUp() {
        customerOrderService = new CustomerOrderService();
        mockCustomerOrderRepository = Mockito.mock(CustomerOrderRepository.class);
        mockProductRepository = Mockito.mock(ProductRepository.class);

        customerOrderService.setRepository(mockCustomerOrderRepository);
        customerOrderService.setProductRepository(mockProductRepository);
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

//    @Test
//    public void testPost() throws Exception {
//
//    }
//
//    @Test
//    public void testUpdate() throws Exception {
//
//    }
}