package service;

import entities.Product;
import entity.BuildStatus;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import peaseloxes.toolbox.util.IOUtil;
import repository.BuildRepository;
import repository.ProductRepository;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;

/**
 * Created by alex on 1/13/16.
 */
@RunWith(PowerMockRunner.class)
public class DBWriterTest {

    private BuildRepository mockBuildRepository;
    private ProductRepository mockProductRepository;

    private DBWriter<Product> dbWriter;


    @Before
    public void setUp() throws Exception {
        mockBuildRepository = Mockito.mock(BuildRepository.class);
        mockProductRepository = Mockito.mock(ProductRepository.class);
        dbWriter = new DBWriter<>();
        dbWriter.setMax(1);
        dbWriter.setMin(0);
        dbWriter.setBuildRepository(mockBuildRepository);
    }

    @PrepareForTest(IOUtil.class)
    @Test
//    @Ignore(value = "Static not being mocked properly, multi thread confusing for coverage analyser, log4j mocking issues")
    public void testWrite() throws Exception {
        // actual file writing is done by IOUtil
        PowerMockito.mockStatic(IOUtil.class);
        Mockito.when(mockProductRepository.findAll()).thenReturn(Arrays.asList(new Product()));
        Mockito.when(mockBuildRepository.save(any(BuildStatus.class))).thenReturn(new BuildStatus("1"));
        Mockito.when(mockBuildRepository.exists("1")).thenReturn(true);
        // no errors
        dbWriter.write(mockProductRepository, Product.class, "1");
        try {
            new DBWriter<BuildStatus>().write(Mockito.mock(BuildRepository.class), BuildStatus.class, "1");
            fail("illegal argument expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    @PrepareForTest(IOUtil.class)
    @Test
    public void testDoWritingHereBecauseUnitTestingSucksOtherWise() throws Exception {
        PowerMockito.mockStatic(IOUtil.class);
        Mockito.when(mockProductRepository.findAll()).thenReturn(Arrays.asList(new Product()));
        Mockito.when(mockBuildRepository.save(any(BuildStatus.class))).thenReturn(new BuildStatus("1"));
        Mockito.when(mockBuildRepository.exists("1")).thenReturn(true);
        Mockito.when(mockBuildRepository.findOne("1")).thenReturn(new BuildStatus("1"));
        // no errors
        dbWriter.doWritingHereBecauseUnitTestingSucksOtherWise(mockProductRepository, "1");
    }
}