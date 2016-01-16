package service;

import entities.Address;
import entities.Customer;
import entities.rest.CustomerOrder;
import entities.rest.CustomerProduct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import repository.AddressRepository;
import repository.CustomerOrderRepository;
import repository.CustomerRepository;
import rest.util.HateoasResponse;
import rest.util.HateoasUtil;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;

/**
 * Created by alex on 1/13/16.
 */
@RunWith(PowerMockRunner.class)
public class CustomerServiceTest {

    private CustomerRepository mockCustomerRepository;
    private CustomerOrderRepository mockCustomerOrderRepository;

    private CustomerService service;
    private CustomerOrder testOrder;
    private Link testLink;
    private ResponseEntity<HateoasResponse> testEntity;
    private AddressRepository mockAddressRepository;

    @Before
    public void setUp() {
        mockCustomerRepository = Mockito.mock(CustomerRepository.class);
        mockAddressRepository = Mockito.mock(AddressRepository.class);
        mockCustomerOrderRepository = Mockito.mock(CustomerOrderRepository.class);
        service = new CustomerService();
        service.setRepository(mockCustomerRepository);
        service.setAddressRepository(mockAddressRepository);
        service.setCustomerOrderRepository(mockCustomerOrderRepository);

        testOrder = CustomerOrder.builder()
                .orderId("1")
                .customerId("1")
                .orderStatus("OPEN")
                .deliveryStatus("NOT SCHEDULED")
                .totalPrice(0)
                .build();
        testOrder.setId("1");
        testLink = new Link("Foo", "Bar");
        CustomerProduct testProduct1 = new CustomerProduct("1", "TEST", 1, 0.00);
        CustomerProduct testProduct2 = new CustomerProduct("2", "TEST", 2, 2.00);
        List<CustomerProduct> testList = new ArrayList<>();
        testList.add(testProduct1);
        testList.add(testProduct2);
        testOrder.setProducts(testList);
        HateoasResponse response = new HateoasResponse(testOrder);
        testEntity = new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Test
    public void testInitRepository() throws Exception {
        Mockito.when(mockCustomerRepository.save(any(Customer.class))).thenReturn(new Customer());
        Mockito.when(mockAddressRepository.save(any(Address.class))).thenReturn(new Address());
        // no errors
        service.initRepository();
    }

    @PrepareForTest(HateoasUtil.class)
    @Test
    public void testPost() throws Exception {
        PowerMockito.mockStatic(HateoasUtil.class);
        Customer testCustomer = new Customer();
        testCustomer.setAddress(new Address());
        testCustomer.setDeliveryAddress(new Address());
        PowerMockito.when(HateoasUtil.build(any(Customer.class))).thenReturn(testEntity);
        PowerMockito.when(HateoasUtil.build(any(Address.class))).thenReturn(testEntity);
        Mockito.when(mockAddressRepository.findByZipcodeAndNumber(any(String.class), any(String.class))).thenReturn(new Address());
        Mockito.when(mockCustomerRepository.save(any(Customer.class))).thenReturn(new Customer());

        // no need to test everything, mock returns a customerorder so that will do
        assertThat(service.post(testCustomer, null).getBody().getContent(), instanceOf(CustomerOrder.class));

        Mockito.when(mockAddressRepository.findByZipcodeAndNumber(any(String.class), any(String.class))).thenReturn(null).thenReturn(null);

        // no need to test everything, mock returns a customerorder so that will do
        assertThat(service.post(testCustomer, null).getBody().getContent(), instanceOf(CustomerOrder.class));
    }

    @PrepareForTest(HateoasUtil.class)
    @Test
    public void testGetByCustomer() throws Exception {
        PowerMockito.mockStatic(HateoasUtil.class);
        PowerMockito.when(HateoasUtil.build(any(CustomerOrder.class))).thenReturn(testEntity);
        Mockito.when(mockCustomerOrderRepository.findByCustomerId("1")).thenReturn(Arrays.asList(testOrder));
        HttpEntity<HateoasResponse> obj = service.getByCustomer("1", null);
        assertThat(obj.getBody().getContent(), instanceOf(CustomerOrder.class));
    }

    @Test
    public void testGetClazz() throws Exception {
        assertThat(service.getClazz().equals(CustomerService.class), is(true));
    }
}