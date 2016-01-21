package util;

import entities.Address;
import entities.Customer;
import entities.rest.CustomerOrder;
import entities.rest.CustomerProduct;
import java.time.Instant;
import java.util.Arrays;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.assertThat;

/**
 * Created by alex on 1/21/16.
 */
public class EntityConverterTest {

    private static CustomerProduct testCustomerProduct;
    private static CustomerOrder testCustomerOrder;
    private static Customer testCustomer;

    @BeforeClass
    public static void setUp() throws Exception {
        testCustomerProduct = new CustomerProduct("1", "pure gold", 50, 0.55);
        testCustomerOrder = new CustomerOrder(
                "1",
                "1",
                "busy",
                "non existent",
                50 * 0.55,
                Instant.now().toEpochMilli(),
                Arrays.asList(testCustomerProduct)
        );
        testCustomer = new Customer();
        testCustomer.setId("1");
        testCustomer.setAddress(new Address());
        testCustomer.setDeliveryAddress(new Address());
    }

    @Test
    public void testConstructor() throws Exception {
        assertThat(TestUtil.constructorIsPrivate(EntityConverter.class), is(true));
    }

    @Test
    public void testConvertProduct() throws Exception {
        assertThat(EntityConverter.convert(testCustomerProduct).getBusinessKey(), is("PROD_b81ef0b2a6ca0e0727dd0179300a9cb7d1cab1aa"));
        assertThat(testCustomerProduct.getBusinessKey(), is("PROD_b81ef0b2a6ca0e0727dd0179300a9cb7d1cab1aa"));
    }

    @Test
    public void testConvertOrder() throws Exception {
        // apparently order has a different hash every time
        assertThat(EntityConverter.convert(testCustomerOrder).getBusinessKey(), startsWith("ORD"));
        assertThat(testCustomerOrder.getBusinessKey(), startsWith("ORD"));
        assertThat(testCustomerOrder.getProducts().get(0).getBusinessKey(), is("PROD_b81ef0b2a6ca0e0727dd0179300a9cb7d1cab1aa"));

    }

    @Test
    public void testConvertCustomer() throws Exception {
        assertThat(EntityConverter.convert(testCustomer).getBusinessKey(), startsWith("CUST"));
        assertThat(testCustomer.getBusinessKey(), startsWith("CUST"));
    }

    @Test
    public void testConvertWrong() throws Exception {
        assertThat(EntityConverter.convert(null).getBusinessKey().equals(""), is(true));
    }
}