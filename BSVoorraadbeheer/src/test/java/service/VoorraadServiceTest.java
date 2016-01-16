package service;

import entities.Product;
import entities.rest.CustomerOrder;
import entity.BuildStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import repository.BuildRepository;
import repository.ProductRepository;
import rest.util.HateoasResponse;
import rest.util.HateoasUtil;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

/**
 * Created by alex on 1/13/16.
 */
@RunWith(PowerMockRunner.class)
public class VoorraadServiceTest {

    private VoorraadService service;

    private ProductRepository mockProductRepository;
    private BuildRepository mockBuildRepository;
    private DBWriter<Product> mockDBWriter;

    private ResponseEntity<HateoasResponse> testEntity;
    private final String mockIdentifier = "1234hoedjevanhoedjevan";

    private BuildStatus statusBusy;
    private BuildStatus statusReady;

    @Before
    public void setUp() throws Exception {
        service = new VoorraadService();
        mockProductRepository = Mockito.mock(ProductRepository.class);
        mockBuildRepository = Mockito.mock(BuildRepository.class);
        mockDBWriter = Mockito.mock(DBWriter.class);
        service.setProductRepository(mockProductRepository);
        service.setBuildRepository(mockBuildRepository);
        service.setWriter(mockDBWriter);
        HateoasResponse response = new HateoasResponse(mockIdentifier);
        testEntity = new ResponseEntity<>(response, HttpStatus.OK);

        statusBusy = new BuildStatus(mockIdentifier);
        statusBusy.setReady(false);
        statusReady = new BuildStatus(mockIdentifier);
        statusReady.setReady(true);
    }

    @Test
    public void testInitRepository() throws Exception {
        Mockito.when(mockProductRepository.save(any(Product.class))).thenReturn(new Product());
        // no errors
        service.initRepository();
    }

    @Test
    public void testGetAll() throws Exception {
        testResponseMethodNotAllowed(service.getAll(null));
    }

    @Test
    public void testGetById() throws Exception {
        testResponseMethodNotAllowed(service.getById("woop", null));
    }

    @Test
    public void testPost() throws Exception {
        testResponseMethodNotAllowed(service.post(null, null));
    }

    @Test
    public void testDelete() throws Exception {
        testResponseMethodNotAllowed(service.delete("di", null));
    }

    @Test
    public void testUpdate() throws Exception {
        testResponseMethodNotAllowed(service.update("doop", null, null));
    }

    @PrepareForTest(HateoasUtil.class)
    @Test
    public void testRequestBuild() throws Exception {
        PowerMockito.mockStatic(HateoasUtil.class);
        PowerMockito.when(HateoasUtil.build(any(CustomerOrder.class))).thenReturn(testEntity);
        Mockito.doNothing().when(mockDBWriter).write(eq(mockProductRepository), eq(Product.class), eq(mockIdentifier));
        assertThat(service.requestBuild(null).getBody().getContent(), is(mockIdentifier));
    }

    @PrepareForTest(HateoasUtil.class)
    @Test
    public void testRequestStatus() throws Exception {
        PowerMockito.mockStatic(HateoasUtil.class);
        Mockito.when(mockBuildRepository.exists(mockIdentifier)).thenReturn(true);

        PowerMockito.when(HateoasUtil.build(any(CustomerOrder.class))).thenReturn(
                new ResponseEntity<>(new HateoasResponse(false), HttpStatus.OK)
        );
        Mockito.when(mockBuildRepository.findOne(mockIdentifier)).thenReturn(statusBusy);
        assertThat(service.requestStatus(mockIdentifier, null).getBody().getContent(), is(false));

        PowerMockito.when(HateoasUtil.build(any(CustomerOrder.class))).thenReturn(
                new ResponseEntity<>(new HateoasResponse(true), HttpStatus.OK)
        );
        Mockito.when(mockBuildRepository.findOne(mockIdentifier)).thenReturn(statusBusy);
        assertThat(service.requestStatus(mockIdentifier, null).getBody().getContent(), is(true));


        Mockito.when(mockBuildRepository.exists(mockIdentifier)).thenReturn(false);
        assertThat(service.requestStatus(mockIdentifier, null).hasBody(), is(false));
    }

    @Test
    public void testGetClazz() throws Exception {
        assertThat(service.getClazz().equals(VoorraadService.class), is(true));
    }

    private void testResponseMethodNotAllowed(final HttpEntity<HateoasResponse> entity) {
        assertThat(entity.hasBody(), is(false));
        assertThat(entity.toString(), is("<405 Method Not Allowed,{}>"));
    }
}