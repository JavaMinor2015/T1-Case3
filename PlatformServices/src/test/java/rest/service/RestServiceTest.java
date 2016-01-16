package rest.service;

import java.util.Arrays;
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
import peaseloxes.toolbox.util.testUtil.TestObject;
import peaseloxes.toolbox.util.testUtil.TestService;
import rest.repository.RestRepository;
import rest.util.HateoasResponse;
import rest.util.HateoasUtil;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

/**
 * Created by alex on 1/11/16.
 */
@RunWith(PowerMockRunner.class)
public class RestServiceTest {

    private RestService<TestObject> service;
    private RestRepository<TestObject> mockRepository;
    private TestObject testObject;
    private HttpEntity<HateoasResponse> testEntity;
    private Link testLink;

    @Before
    public void setUp() throws Exception {
        service = new TestService();
        mockRepository = Mockito.mock(RestRepository.class);
        service.setRestRepository(mockRepository);
        testObject = new TestObject();
        testObject.setId("1");
        testLink = new Link("Foo", "Bar");
        HateoasResponse response = new HateoasResponse(testObject);
        testEntity = new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Test
    public void testInitRepository() throws Exception {
        // no errors, is not abstract anyway
        service.initRepository();
    }

    @Test
    public void testGetClazz() throws Exception {
        // why wouldn't it be, getClazz is not abstract
        assertTrue(service.getClazz().equals(TestService.class));
    }

    @PrepareForTest(HateoasUtil.class)
    @Test
    public void testGetById() throws Exception {
        PowerMockito.mockStatic(HateoasUtil.class);
        PowerMockito.when(HateoasUtil.build(any(TestObject.class))).thenReturn(testEntity);
        assertThat(service.getById("1", null), is(testEntity));
    }

    @PrepareForTest(HateoasUtil.class)
    @Test
    public void testGetAll() throws Exception {
        PowerMockito.mockStatic(HateoasUtil.class);
        PowerMockito.when(HateoasUtil.build(any(TestObject.class))).thenReturn(testEntity);
        Mockito.when(mockRepository.findAll()).thenReturn(Arrays.asList(testObject));
        assertThat(service.getAll(null), is(testEntity));
    }

    @PrepareForTest(HateoasUtil.class)
    @Test
    public void testPost() throws Exception {
        PowerMockito.mockStatic(HateoasUtil.class);
        PowerMockito.when(HateoasUtil.build(any(TestObject.class))).thenReturn(testEntity);
        Mockito.when(mockRepository.save(any(TestObject.class))).thenReturn(testObject);
        assertThat(service.post(testObject, null), is(testEntity));
    }

    @PrepareForTest(HateoasUtil.class)
    @Test
    public void testUpdate() throws Exception {
        PowerMockito.mockStatic(HateoasUtil.class);
        PowerMockito.when(HateoasUtil.build(any(TestObject.class))).thenReturn(testEntity);
        Mockito.when(mockRepository.save(any(TestObject.class))).thenReturn(testObject);
        assertThat(service.update("1", testObject, null), is(testEntity));
        assertThat(service.update("2", testObject, null), is(testEntity));

    }

    @PrepareForTest(HateoasUtil.class)
    @Test
    public void testDelete() throws Exception {
        PowerMockito.mockStatic(HateoasUtil.class);
        PowerMockito.when(HateoasUtil.build(any(TestObject.class))).thenReturn(testEntity);
        Mockito.doNothing().when(mockRepository).delete(eq("1"));
        assertThat(service.delete("1", null).getBody().getContent(), is(testEntity.getBody().getContent()));
    }

    @Test
    public void testOptions() throws Exception {
        assertThat(((ResponseEntity) service.options()).getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void testSetRestRepository() throws Exception {
        // no errors, already tested heaps
        service.setRestRepository(null);
    }
}