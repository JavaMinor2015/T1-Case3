package peaseloxes.spring.aspect;

import auth.service.AuthService;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import peaseloxes.spring.annotations.LoginRequired;
import rest.util.HateoasResponse;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;

/**
 * @author peaseloxes
 */
@RunWith(PowerMockRunner.class)
public class TokenAspectTest {
    private TokenAspect aspect;
    private AuthService mockService;
    private ProceedingJoinPoint mockJointPoint;
    private LoginRequired mockLoginRequired;
    private HttpServletRequest mockServletRequest;
    private String validHeader = "valid";
    private String invalidHeader = "invalid";
    private Object testObject = new Object();

    @Before
    public void setUp() throws Throwable {
        aspect = new TokenAspect();

        mockService = Mockito.mock(AuthService.class);
        Mockito.when(mockService.isAuthorized(eq(validHeader))).thenReturn(true);
        Mockito.when(mockService.isAuthorized(eq(invalidHeader))).thenReturn(false);
        aspect.setAuthService(mockService);

        mockJointPoint = Mockito.mock(ProceedingJoinPoint.class);
        Mockito.when(mockJointPoint.proceed()).thenReturn(testObject);

        mockLoginRequired = Mockito.mock(LoginRequired.class);
        mockServletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(mockJointPoint.getArgs()).thenReturn(new Object[]{mockServletRequest});
    }

    @PrepareForTest(AspectUtil.class)
    @Test
    public void testHandleLinkAnnotation() throws Throwable {
        PowerMockito.mockStatic(AspectUtil.class);
        PowerMockito.when(AspectUtil.imAnUntestableHorrorAndDoNotDeserveToBeInTheSameClass(Matchers.any(), Matchers.any())).thenReturn(true);
        Mockito.when(mockLoginRequired.value()).thenReturn(false);
        assertThat(aspect.handleLinkAnnotation(
                mockJointPoint,
                mockLoginRequired),
                is(testObject));

        Mockito.when(mockLoginRequired.value()).thenReturn(true);
        Mockito.when(mockServletRequest.getHeader(eq("Authorization"))).thenReturn(validHeader);
        assertThat(aspect.handleLinkAnnotation(
                mockJointPoint,
                mockLoginRequired),
                is(testObject));

        Mockito.when(mockServletRequest.getHeader(eq("Authorization"))).thenReturn(invalidHeader);
        ResponseEntity<HateoasResponse> response = (ResponseEntity<HateoasResponse>) aspect.handleLinkAnnotation(
                mockJointPoint,
                mockLoginRequired);
        assertThat(response.getStatusCode(),
                is(HttpStatus.FORBIDDEN));


        Mockito.when(mockJointPoint.getArgs()).thenReturn(new Object[]{});
        assertThat(((ResponseEntity<HateoasResponse>) aspect.handleLinkAnnotation(
                mockJointPoint,
                mockLoginRequired)).getStatusCode(),
                is(HttpStatus.FORBIDDEN));
    }

    @PrepareForTest(AspectUtil.class)
    @Test
    public void testFalseResponse() throws Throwable {
        PowerMockito.mockStatic(AspectUtil.class);
        PowerMockito.when(AspectUtil.imAnUntestableHorrorAndDoNotDeserveToBeInTheSameClass(Matchers.any(), Matchers.any())).thenReturn(false);
        // not a response entity
        Mockito.when(mockJointPoint.getArgs()).thenReturn(new Object[]{mockServletRequest});
        Mockito.when(mockJointPoint.proceed())
                .thenReturn("OMG this is not a HttpEntity");
        assertThat(((ResponseEntity<HateoasResponse>) aspect.handleLinkAnnotation(
                mockJointPoint,
                mockLoginRequired)).getStatusCode(),
                is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void testHundredPercentFTW() throws Exception {
        final Method singleAnnotation = TokenAspect.class.getDeclaredMethod("hasLogin", LoginRequired.class);
        assertThat(singleAnnotation.isAccessible(), is(false));
        singleAnnotation.setAccessible(true);
        singleAnnotation.invoke(aspect, mockLoginRequired);
    }
}
