package peaseloxes.spring.aspect;

/**
 * @author peaseloxes
 */
public class HateoasLinkAspectTest {
//    // // FIXME: 1/19/16 test\
//
//    private HateoasLinkAspect aspect;
//    private ProceedingJoinPoint mockJointPoint;
//    private WrapWithLink mockWrapWithLink;
//    private WrapWithLinks mockWrapWithLinks;
//    private HttpServletRequest mockServletRequest;
//
//    @Before
//    public void setUp() throws Exception {
//        aspect = new HateoasLinkAspect();
//        mockJointPoint = Mockito.mock(ProceedingJoinPoint.class);
//        mockWrapWithLink = Mockito.mock(WrapWithLink.class);
//        mockWrapWithLinks = Mockito.mock(WrapWithLinks.class);
//        Mockito.when(mockWrapWithLinks.links()).thenReturn(new WrapWithLink[]{mockWrapWithLink});
//
//        mockServletRequest = Mockito.mock(HttpServletRequest.class);
//        Mockito.when(mockServletRequest.getScheme()).thenReturn("http");
//        Mockito.when(mockServletRequest.getServerName()).thenReturn("localhost");
//        Mockito.when(mockServletRequest.getServerPort()).thenReturn(8080);
//        Mockito.when(mockServletRequest.getServletPath()).thenReturn("/im/a/link/to/a/method");
//    }
//
//    @Test
//    public void testHandleLinkAnnotation() throws Throwable {
//        // a response entity
//        Mockito.when(mockJointPoint.proceed())
//                .thenReturn(new ResponseEntity<>(new HateoasResponse("woop"), HttpStatus.OK));
//        Mockito.when(mockWrapWithLink.link()).thenReturn("/link");
//        Mockito.when(mockWrapWithLink.rel()).thenReturn(WrapWithLink.Type.DEFAULT);
//        assertThat(aspect.handleLinkAnnotation(mockJointPoint, mockWrapWithLink, mockServletRequest), instanceOf(HttpEntity.class));
//        HttpEntity<HateoasResponse> response = HttpEntity.class.cast(aspect.handleLinkAnnotation(mockJointPoint, mockWrapWithLink, mockServletRequest));
//        assertThat(response.getBody().getLink("self").getHref(), is("http://localhost:8080/link"));
//        assertThat(response.getBody().getLink("next").getHref(), is("http://localhost:8080/link"));
//        assertThat(response.getBody().getLink("prev").getHref(), is("http://localhost:8080/link"));
//        assertThat(response.getBody().getLink("update").getHref(), is("http://localhost:8080/link"));
//        assertThat(response.getBody().getLink("delete").getHref(), is("http://localhost:8080/link"));
//
//        // not a response entity
//        Mockito.when(mockJointPoint.proceed())
//                .thenReturn("OMG this is not a HttpEntity");
//        assertThat(aspect.handleLinkAnnotation(mockJointPoint, mockWrapWithLink, mockServletRequest), is("OMG this is not a HttpEntity"));
//    }
//
//    @Test
//    public void testHandleLinksAnnotation() throws Throwable {
//        // a response entity
//        Mockito.when(mockJointPoint.proceed())
//                .thenReturn(new ResponseEntity<>(new HateoasResponse("woop"), HttpStatus.OK));
//        Mockito.when(mockWrapWithLink.link()).thenReturn("/link");
//        Mockito.when(mockWrapWithLink.rel()).thenReturn(WrapWithLink.Type.DEFAULT);
//        assertThat(aspect.handleLinksAnnotation(mockJointPoint, mockWrapWithLinks, mockServletRequest), instanceOf(HttpEntity.class));
//        HttpEntity<HateoasResponse> response = HttpEntity.class.cast(aspect.handleLinksAnnotation(mockJointPoint, mockWrapWithLinks, mockServletRequest));
//        assertThat(response.getBody().getLink("self").getHref(), is("http://localhost:8080/link"));
//        assertThat(response.getBody().getLink("next").getHref(), is("http://localhost:8080/link"));
//        assertThat(response.getBody().getLink("prev").getHref(), is("http://localhost:8080/link"));
//        assertThat(response.getBody().getLink("update").getHref(), is("http://localhost:8080/link"));
//        assertThat(response.getBody().getLink("delete").getHref(), is("http://localhost:8080/link"));
//
//        // not a response entity
//        Mockito.when(mockJointPoint.proceed())
//                .thenReturn("OMG this is not a HttpEntity");
//        assertThat(aspect.handleLinksAnnotation(mockJointPoint, mockWrapWithLinks, mockServletRequest), is("OMG this is not a HttpEntity"));
//    }
//
//    @Test
//    public void testWithoutLinks() throws Throwable {
//        // a response entity
//        Mockito.when(mockJointPoint.proceed())
//                .thenReturn(new ResponseEntity<>(new HateoasResponse("woop"), HttpStatus.OK));
//        Mockito.when(mockWrapWithLink.link()).thenReturn("");
//        Mockito.when(mockWrapWithLink.rel()).thenReturn(WrapWithLink.Type.DEFAULT);
//        assertThat(aspect.handleLinkAnnotation(mockJointPoint, mockWrapWithLink, mockServletRequest), instanceOf(HttpEntity.class));
//        HttpEntity<HateoasResponse> response = HttpEntity.class.cast(aspect.handleLinkAnnotation(mockJointPoint, mockWrapWithLink, mockServletRequest));
//        assertThat(response.getBody().getLink("self").getHref(), is("http://localhost:8080/im/a/link/to/a/method"));
//        assertThat(response.getBody().getLink("next").getHref(), is("http://localhost:8080/im/a/link/to/a/method"));
//        assertThat(response.getBody().getLink("prev").getHref(), is("http://localhost:8080/im/a/link/to/a/method"));
//        assertThat(response.getBody().getLink("update").getHref(), is("http://localhost:8080/im/a/link/to/a/method"));
//        assertThat(response.getBody().getLink("delete").getHref(), is("http://localhost:8080/im/a/link/to/a/method"));
//    }
//
//    @Test
//    public void testWithCustomLink() throws Throwable {
//        // a response entity
//        Mockito.when(mockJointPoint.proceed())
//                .thenReturn(new ResponseEntity<>(new HateoasResponse("woop"), HttpStatus.OK));
//        Mockito.when(mockWrapWithLink.link()).thenReturn("");
//        Mockito.when(mockWrapWithLink.rel()).thenReturn(WrapWithLink.Type.SELF);
//        assertThat(aspect.handleLinkAnnotation(mockJointPoint, mockWrapWithLink, mockServletRequest), instanceOf(HttpEntity.class));
//        HttpEntity<HateoasResponse> response = HttpEntity.class.cast(aspect.handleLinkAnnotation(mockJointPoint, mockWrapWithLink, mockServletRequest));
//        assertThat(response.getBody().getLink("self").getHref(), is("http://localhost:8080/im/a/link/to/a/method"));
//    }
//
//    @Test
//    public void testHundredPercentFTW() throws Exception {
//        final Method hasRequestParam = HateoasLinkAspect.class.getDeclaredMethod("hasRequestParam", HttpServletRequest.class);
//        assertThat(hasRequestParam.isAccessible(), is(false));
//        hasRequestParam.setAccessible(true);
//        hasRequestParam.invoke(aspect, mockServletRequest);
//
//        final Method singleAnnotation = HateoasLinkAspect.class.getDeclaredMethod("singleAnnotation", WrapWithLink.class);
//        assertThat(singleAnnotation.isAccessible(), is(false));
//        singleAnnotation.setAccessible(true);
//        singleAnnotation.invoke(aspect, mockWrapWithLink);
//
//        final Method multipleAnnotation = HateoasLinkAspect.class.getDeclaredMethod("multipleAnnotation", WrapWithLinks.class);
//        assertThat(multipleAnnotation.isAccessible(), is(false));
//        multipleAnnotation.setAccessible(true);
//        multipleAnnotation.invoke(aspect, mockWrapWithLinks);
//    }
}
