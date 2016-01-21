package peaseloxes.spring.aspect;

import entities.Product;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import peaseloxes.spring.annotations.DataVaultObservable;
import service.DataVaultService;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyVararg;

/**
 * Created by alex on 1/21/16.
 */
public class DataVaultAspectTest {

    private DataVaultAspect aspect;
    private DataVaultService mockService;
    private ProceedingJoinPoint mockJointPoint;
    private DataVaultObservable mockAnnotation;

    @Before
    public void setUp() throws Throwable {
        aspect = new DataVaultAspect();
        mockService = Mockito.mock(DataVaultService.class);
        mockJointPoint = Mockito.mock(ProceedingJoinPoint.class);
        mockAnnotation = Mockito.mock(DataVaultObservable.class);
        aspect.setVaultService(mockService);
        Mockito.when(mockJointPoint.proceed(anyVararg())).thenReturn("original");
    }

    @Test
    public void testHandleVaultAnnotation() throws Throwable {
        Mockito.when(mockJointPoint.getArgs()).thenReturn(new Object[]{new Product()});
        assertThat(aspect.handleVaultAnnotation(mockJointPoint, mockAnnotation), is("original"));

        Mockito.when(mockJointPoint.getArgs()).thenReturn(new Object[]{});
        assertThat(aspect.handleVaultAnnotation(mockJointPoint, mockAnnotation), is("original"));

        Mockito.when(mockJointPoint.getArgs()).thenReturn(new Object[]{new Object()});
        assertThat(aspect.handleVaultAnnotation(mockJointPoint, mockAnnotation), is("original"));
    }

}