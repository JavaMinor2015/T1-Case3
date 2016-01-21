package peaseloxes.spring.aspect;

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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import peaseloxes.spring.annotations.WrapWithLink;
import peaseloxes.spring.annotations.WrapWithLinks;
import rest.util.HateoasResponse;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author peaseloxes
 */
@RunWith(PowerMockRunner.class)
public class HateoasLinkAspectTest {

    private HateoasLinkAspect aspect;
    private ProceedingJoinPoint mockJointPoint;
    private WrapWithLink mockWrapWithLink;
    private WrapWithLinks mockWrapWithLinks;
    private HttpServletRequest mockServletRequest;

    @Before
    public void setUp() throws Exception {
        aspect = new HateoasLinkAspect();
        mockJointPoint = Mockito.mock(ProceedingJoinPoint.class);
        mockWrapWithLink = Mockito.mock(WrapWithLink.class);
        mockWrapWithLinks = Mockito.mock(WrapWithLinks.class);
        Mockito.when(mockWrapWithLinks.links()).thenReturn(new WrapWithLink[]{mockWrapWithLink});
        Mockito.when(mockJointPoint.getArgs()).thenReturn(new Object[]{mockServletRequest});

        mockServletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(mockServletRequest.getScheme()).thenReturn("http");
        Mockito.when(mockServletRequest.getServerName()).thenReturn("localhost");
        Mockito.when(mockServletRequest.getServerPort()).thenReturn(8080);
        Mockito.when(mockServletRequest.getServletPath()).thenReturn("/im/a/link/to/a/method");
    }

    @PrepareForTest(AspectUtil.class)
    @Test
    public void testHandleLinkAnnotation() throws Throwable {
        // a response entity
        PowerMockito.mockStatic(AspectUtil.class);
        PowerMockito.when(AspectUtil.imAnUntestableHorrorAndDoNotDeserveToBeInTheSameClass(Matchers.any(), Matchers.any())).thenReturn(true);
        Mockito.when(mockJointPoint.proceed())
                .thenReturn(new ResponseEntity<>(new HateoasResponse("woop"), HttpStatus.OK));
        Mockito.when(mockJointPoint.getArgs()).thenReturn(new Object[]{mockServletRequest});
        Mockito.when(mockWrapWithLink.link()).thenReturn("/link");
        Mockito.when(mockWrapWithLink.rel()).thenReturn(WrapWithLink.Type.DEFAULT);
        assertThat(aspect.handleLinkAnnotation(mockJointPoint, mockWrapWithLink), instanceOf(HttpEntity.class));
        HttpEntity<HateoasResponse> response = HttpEntity.class.cast(aspect.handleLinkAnnotation(mockJointPoint, mockWrapWithLink));
        assertThat(response.getBody().getLink("self").getHref(), is("http://localhost:8080/link"));
        assertThat(response.getBody().getLink("next").getHref(), is("http://localhost:8080/link"));
        assertThat(response.getBody().getLink("prev").getHref(), is("http://localhost:8080/link"));
        assertThat(response.getBody().getLink("update").getHref(), is("http://localhost:8080/link"));
        assertThat(response.getBody().getLink("delete").getHref(), is("http://localhost:8080/link"));

        // not a response entity
        PowerMockito.when(AspectUtil.imAnUntestableHorrorAndDoNotDeserveToBeInTheSameClass(Matchers.any(), Matchers.any())).thenReturn(false);
        Mockito.when(mockJointPoint.proceed())
                .thenReturn("OMG this is not a HttpEntity");
        assertThat(aspect.handleLinkAnnotation(mockJointPoint, mockWrapWithLink), is("OMG this is not a HttpEntity"));

        // not a response entity
        PowerMockito.when(AspectUtil.imAnUntestableHorrorAndDoNotDeserveToBeInTheSameClass(Matchers.any(), Matchers.any())).thenReturn(true).thenReturn(false);
        Mockito.when(mockJointPoint.proceed())
                .thenReturn("OMG this is not a HttpEntity");
        assertThat(aspect.handleLinkAnnotation(mockJointPoint, mockWrapWithLink), is("OMG this is not a HttpEntity"));
    }

    @PrepareForTest(AspectUtil.class)
    @Test
    public void testHandleLinksAnnotation() throws Throwable {
        // a response entity
        PowerMockito.mockStatic(AspectUtil.class);
        PowerMockito.when(AspectUtil.imAnUntestableHorrorAndDoNotDeserveToBeInTheSameClass(Matchers.any(), Matchers.any())).thenReturn(true);
        Mockito.when(mockJointPoint.proceed())
                .thenReturn(new ResponseEntity<>(new HateoasResponse("woop"), HttpStatus.OK));
        Mockito.when(mockJointPoint.getArgs()).thenReturn(new Object[]{mockServletRequest});
        Mockito.when(mockWrapWithLink.link()).thenReturn("/link");
        Mockito.when(mockWrapWithLink.rel()).thenReturn(WrapWithLink.Type.DEFAULT);
        assertThat(aspect.handleLinksAnnotation(mockJointPoint, mockWrapWithLinks), instanceOf(HttpEntity.class));
        HttpEntity<HateoasResponse> response = HttpEntity.class.cast(aspect.handleLinksAnnotation(mockJointPoint, mockWrapWithLinks));
        assertThat(response.getBody().getLink("self").getHref(), is("http://localhost:8080/link"));
        assertThat(response.getBody().getLink("next").getHref(), is("http://localhost:8080/link"));
        assertThat(response.getBody().getLink("prev").getHref(), is("http://localhost:8080/link"));
        assertThat(response.getBody().getLink("update").getHref(), is("http://localhost:8080/link"));
        assertThat(response.getBody().getLink("delete").getHref(), is("http://localhost:8080/link"));

        // not a response entity
        PowerMockito.when(AspectUtil.imAnUntestableHorrorAndDoNotDeserveToBeInTheSameClass(Matchers.any(), Matchers.any())).thenReturn(false);
        Mockito.when(mockJointPoint.proceed())
                .thenReturn("OMG this is not a HttpEntity");
        assertThat(aspect.handleLinksAnnotation(mockJointPoint, mockWrapWithLinks), is("OMG this is not a HttpEntity"));

        // not a response entity
        PowerMockito.when(AspectUtil.imAnUntestableHorrorAndDoNotDeserveToBeInTheSameClass(Matchers.any(), Matchers.any())).thenReturn(true).thenReturn(false);
        Mockito.when(mockJointPoint.proceed())
                .thenReturn("OMG this is not a HttpEntity");
        assertThat(aspect.handleLinksAnnotation(mockJointPoint, mockWrapWithLinks), is("OMG this is not a HttpEntity"));
    }

    @PrepareForTest(AspectUtil.class)
    @Test
    public void testWithoutLinks() throws Throwable {
        // a response entity
        PowerMockito.mockStatic(AspectUtil.class);
        PowerMockito.when(AspectUtil.imAnUntestableHorrorAndDoNotDeserveToBeInTheSameClass(Matchers.any(), Matchers.any())).thenReturn(true);
        Mockito.when(mockJointPoint.proceed())
                .thenReturn(new ResponseEntity<>(new HateoasResponse("woop"), HttpStatus.OK));
        Mockito.when(mockJointPoint.getArgs()).thenReturn(new Object[]{mockServletRequest});
        Mockito.when(mockWrapWithLink.link()).thenReturn("");
        Mockito.when(mockWrapWithLink.rel()).thenReturn(WrapWithLink.Type.DEFAULT);
        assertThat(aspect.handleLinkAnnotation(mockJointPoint, mockWrapWithLink), instanceOf(HttpEntity.class));
        HttpEntity<HateoasResponse> response = HttpEntity.class.cast(aspect.handleLinkAnnotation(mockJointPoint, mockWrapWithLink));
        assertThat(response.getBody().getLink("self").getHref(), is("http://localhost:8080/im/a/link/to/a/method"));
        assertThat(response.getBody().getLink("next").getHref(), is("http://localhost:8080/im/a/link/to/a/method"));
        assertThat(response.getBody().getLink("prev").getHref(), is("http://localhost:8080/im/a/link/to/a/method"));
        assertThat(response.getBody().getLink("update").getHref(), is("http://localhost:8080/im/a/link/to/a/method"));
        assertThat(response.getBody().getLink("delete").getHref(), is("http://localhost:8080/im/a/link/to/a/method"));
    }

    @PrepareForTest(AspectUtil.class)
    @Test
    public void testWithCustomLink() throws Throwable {
        // a response entity
        PowerMockito.mockStatic(AspectUtil.class);
        PowerMockito.when(AspectUtil.imAnUntestableHorrorAndDoNotDeserveToBeInTheSameClass(Matchers.any(), Matchers.any())).thenReturn(true);
        Mockito.when(mockJointPoint.proceed())
                .thenReturn(new ResponseEntity<>(new HateoasResponse("woop"), HttpStatus.OK));
        Mockito.when(mockJointPoint.getArgs()).thenReturn(new Object[]{mockServletRequest});
        Mockito.when(mockWrapWithLink.link()).thenReturn("");
        Mockito.when(mockWrapWithLink.rel()).thenReturn(WrapWithLink.Type.SELF);
        assertThat(aspect.handleLinkAnnotation(mockJointPoint, mockWrapWithLink), instanceOf(HttpEntity.class));
        HttpEntity<HateoasResponse> response = HttpEntity.class.cast(aspect.handleLinkAnnotation(mockJointPoint, mockWrapWithLink));
        assertThat(response.getBody().getLink("self").getHref(), is("http://localhost:8080/im/a/link/to/a/method"));
    }

    @Test
    public void testHundredPercentFTW() throws Exception {
        final Method singleAnnotation = HateoasLinkAspect.class.getDeclaredMethod("singleAnnotation", WrapWithLink.class);
        assertThat(singleAnnotation.isAccessible(), is(false));
        singleAnnotation.setAccessible(true);
        singleAnnotation.invoke(aspect, mockWrapWithLink);

        final Method multipleAnnotation = HateoasLinkAspect.class.getDeclaredMethod("multipleAnnotation", WrapWithLinks.class);
        assertThat(multipleAnnotation.isAccessible(), is(false));
        multipleAnnotation.setAccessible(true);
        multipleAnnotation.invoke(aspect, mockWrapWithLinks);
    }
}
