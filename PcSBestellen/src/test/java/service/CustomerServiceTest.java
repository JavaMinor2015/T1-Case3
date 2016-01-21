package service;

import auth.repository.TokenRepository;
import auth.repository.UserRepository;
import entities.Address;
import entities.Customer;
import entities.auth.Token;
import entities.auth.User;
import entities.rest.CustomerOrder;
import entities.rest.CustomerProduct;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
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
import static org.mockito.Matchers.eq;

/**
 * Created by alex on 1/13/16.
 */
@RunWith(PowerMockRunner.class)
public class CustomerServiceTest {

    private CustomerRepository mockCustomerRepository;
    private CustomerOrderRepository mockCustomerOrderRepository;
    private TokenRepository mockTokenRepository;
    private HttpServletRequest mockRequest;

    private CustomerService service;
    private CustomerOrder testOrder;
    private Link testLink;
    private Token testToken;
    private User testUser;
    private ResponseEntity<HateoasResponse> testEntity;
    private AddressRepository mockAddressRepository;
    private UserRepository mockUserRepository;

    @Before
    public void setUp() {
        mockCustomerRepository = Mockito.mock(CustomerRepository.class);
        mockAddressRepository = Mockito.mock(AddressRepository.class);
        mockCustomerOrderRepository = Mockito.mock(CustomerOrderRepository.class);
        mockTokenRepository = Mockito.mock(TokenRepository.class);
        mockUserRepository = Mockito.mock(UserRepository.class);
        mockRequest = Mockito.mock(HttpServletRequest.class);

        testToken = new Token("godmode=true");
        testToken.setUserId("1");
        testToken.setCustId("1");
        testToken.setTimestamp(Instant.now().toEpochMilli());

        testUser = new User();
        testUser.setCustomerId("1");
        testUser.setEmail("me@company.com");
        testUser.setPassword("oracle123");
        testUser.setId("1");

        Mockito.when(mockTokenRepository.findByToken(any(String.class))).thenReturn(testToken);
        Mockito.when(mockTokenRepository.save(any(Token.class))).thenReturn(testToken);
        Mockito.when(mockRequest.getHeader(eq("Authorization"))).thenReturn("Bearer godmode=true");
        Mockito.when(mockUserRepository.findOne(any(String.class))).thenReturn(testUser);

        service = new CustomerService();
        service.setRepository(mockCustomerRepository);
        service.setAddressRepository(mockAddressRepository);
        service.setCustomerOrderRepository(mockCustomerOrderRepository);
        service.setTokenRepository(mockTokenRepository);
        service.setUserRepository(mockUserRepository);

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
        PowerMockito.when(HateoasUtil.toHateoas(any(Customer.class), Matchers.<Link[]>anyVararg())).thenReturn(new HateoasResponse(new Customer()));
        Mockito.when(mockAddressRepository.findByZipcodeAndNumber(any(String.class), any(String.class))).thenReturn(new Address()).thenReturn(new Address());
        Mockito.when(mockCustomerRepository.save(any(Customer.class))).thenReturn(new Customer());

        assertThat(service.post(testCustomer, mockRequest).getBody().getContent(), instanceOf(Customer.class));

        Mockito.when(mockAddressRepository.findByZipcodeAndNumber(any(String.class), any(String.class))).thenReturn(null).thenReturn(null);

        assertThat(service.post(testCustomer, mockRequest).getBody().getContent(), instanceOf(Customer.class));

        assertThat(service.post(testCustomer, mockRequest).getBody().getContent(), instanceOf(Customer.class));

        assertThat(service.post(testCustomer, mockRequest).getBody().getContent(), instanceOf(Customer.class));
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

    @PrepareForTest(HateoasUtil.class)
    @Test
    public void testGetCustomer() throws Exception {
        PowerMockito.mockStatic(HateoasUtil.class);
        PowerMockito.when(HateoasUtil.build(any(Customer.class))).thenReturn(new ResponseEntity<>(new HateoasResponse(new Customer()), HttpStatus.OK));
        assertThat(service.getCustomer(mockRequest).getBody().getContent(), instanceOf(Customer.class));
    }
}