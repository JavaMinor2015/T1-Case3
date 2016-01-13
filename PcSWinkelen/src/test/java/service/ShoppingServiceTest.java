package service;

import entities.Product;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import repository.ProductRepository;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by alex on 1/13/16.
 */
public class ShoppingServiceTest {

    private ShoppingService service;
    private ProductRepository repository;

    @Before
    public void setUp() {
        service = new ShoppingService();
        repository = Mockito.mock(ProductRepository.class);
        service.setRepository(repository);
    }

    @Test
    public void testInitRepository() throws Exception {
        when(repository.save(any(Product.class))).thenReturn(new Product());
        // no errors
        service.initRepository();
    }

    @Test
    public void testGetClazz() throws Exception {
        assertTrue(service.getClazz().equals(ShoppingService.class));
    }
}