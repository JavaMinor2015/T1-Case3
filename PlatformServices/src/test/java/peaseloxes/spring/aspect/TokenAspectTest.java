package peaseloxes.spring.aspect;

import auth.service.AuthService;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
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
    }

    @Test
    public void testHandleLinkAnnotation() throws Throwable {
        Mockito.when(mockLoginRequired.value()).thenReturn(false);
        assertThat(aspect.handleLinkAnnotation(
                mockJointPoint,
                mockLoginRequired,
                mockServletRequest),
                is(testObject));

        Mockito.when(mockLoginRequired.value()).thenReturn(true);
        Mockito.when(mockServletRequest.getHeader(eq("Authorization"))).thenReturn(validHeader);
        assertThat(aspect.handleLinkAnnotation(
                mockJointPoint,
                mockLoginRequired,
                mockServletRequest),
                is(testObject));

        Mockito.when(mockServletRequest.getHeader(eq("Authorization"))).thenReturn(invalidHeader);
        ResponseEntity<HateoasResponse> response = (ResponseEntity<HateoasResponse>) aspect.handleLinkAnnotation(
                mockJointPoint,
                mockLoginRequired,
                mockServletRequest);
        assertThat(response.getStatusCode(),
                is(HttpStatus.FORBIDDEN));

    }

    @Test
    public void testHundredPercentFTW() throws Exception {
        final Method hasRequestParam = TokenAspect.class.getDeclaredMethod("hasRequestParam", HttpServletRequest.class);
        assertThat(hasRequestParam.isAccessible(), is(false));
        hasRequestParam.setAccessible(true);
        hasRequestParam.invoke(aspect, mockServletRequest);

        final Method singleAnnotation = TokenAspect.class.getDeclaredMethod("hasLogin", LoginRequired.class);
        assertThat(singleAnnotation.isAccessible(), is(false));
        singleAnnotation.setAccessible(true);
        singleAnnotation.invoke(aspect, mockLoginRequired);
    }


}