package peaseloxes.spring.aspect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import peaseloxes.spring.annotations.WrapWithLink;
import peaseloxes.spring.annotations.WrapWithLinks;
import rest.util.HateoasResponse;
import rest.util.HateoasUtil;

/**
 * Aspect for automatically adding Hateoas compliant links.
 * <p>
 * Requires methods to be annotated with {@linkplain WrapWithLink}
 * or {@linkplain WrapWithLinks}.
 *
 * @author peaseloxes
 */
@Aspect
public class HateoasLinkAspect {

    // squid:S00112 is being raised for throwing Throwable
    // hence warning being suppressed as well as the warning
    // for unused private methods when they are clearly pointcut methods

    /**
     * Adds links to methods annotated with @WrapWithLink.
     *
     * @param jointPoint   the original joint point.
     * @param wrapWithLink the annotation.
     * @param request      the servlet request.
     * @return a new HttpEntity with links, or the original response.
     * @throws Throwable try not to get hit.
     */
    @SuppressWarnings("squid:S00112")
    @Around("singleAnnotation(wrapWithLink) && hasRequestParam(request)")
    public Object handleLinkAnnotation(final ProceedingJoinPoint jointPoint,
                                       final WrapWithLink wrapWithLink,
                                       final HttpServletRequest request) throws Throwable {
        final Object response = jointPoint.proceed();
        if (HttpEntity.class.isAssignableFrom(response.getClass())) {
            return addLinks((HttpEntity) response, wrapWithLink, request);
        }
        // nothing we can do but return the original and hope for the best
        return response;
    }

    /**
     * Adds links to methods annotated with @WrapWithLinks.
     *
     * @param jointPoint    the original joint point.
     * @param wrapWithLinks the annotation.
     * @param request       the servlet request.
     * @return a new HttpEntity with links, or the original response.
     * @throws Throwable try not to get hit.
     */
    @SuppressWarnings("squid:S00112")
    @Around("multipleAnnotation(wrapWithLinks) && hasRequestParam(request)")
    public Object handleLinksAnnotation(final ProceedingJoinPoint jointPoint,
                                        final WrapWithLinks wrapWithLinks,
                                        final HttpServletRequest request) throws Throwable {
        final Object response = jointPoint.proceed();
        if (HttpEntity.class.isAssignableFrom(response.getClass())) {
            return addLinks((HttpEntity) response, wrapWithLinks, request);
        }
        // nothing we can do but return the original and hope for the best
        return response;
    }

    /**
     * Adds links to an HttpEntity instance.
     *
     * @param original     the original HttpEntity instance.
     * @param wrapWithLink the WrapWithLink annotation.
     * @param request      the servlet request.
     * @return a new HttpEntity with the links.
     */
    private HttpEntity<HateoasResponse> addLinks(final HttpEntity original,
                                                 final WrapWithLink wrapWithLink,
                                                 final HttpServletRequest request) {
        final Link[] links = processHateoasLink(wrapWithLink, request);
        final HateoasResponse response = new HateoasResponse(original.getBody());
        response.add(links);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Adds links to an HttpEntity instance.
     *
     * @param original      the original HttpEntity instance.
     * @param wrapWithLinks the WrapWithLinks annotation.
     * @param request       the servlet request.
     * @return a new HttpEntity with the links.
     */
    private HttpEntity<HateoasResponse> addLinks(final HttpEntity original,
                                                 final WrapWithLinks wrapWithLinks,
                                                 final HttpServletRequest request) {
        final List<Link> linkList = new ArrayList<>();
        for (int i = 0; i < wrapWithLinks.links().length; i++) {
            linkList.addAll(
                    Arrays.asList(processHateoasLink(wrapWithLinks.links()[i],
                            request))
            );
        }
        final HateoasResponse response = new HateoasResponse(original.getBody());
        final Link[] links = new Link[]{};
        response.add(linkList.toArray(links));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Creates a link array given a WrapWithLink annotation.
     *
     * @param wrapWithLink the WrapWithLink annotation.
     * @param request      the servlet request.
     * @return an array of links.
     */
    private Link[] processHateoasLink(final WrapWithLink wrapWithLink,
                                      final HttpServletRequest request) {


        // get the method uri
        final String methodPath = HateoasUtil.getRootUrl(request)
                + request.getServletPath();

        // if link is empty, use methodPath
        // else use the link provided
        final String pathToUse;
        if (wrapWithLink.link().equals("")) {
            pathToUse = methodPath;
        } else {
            pathToUse = HateoasUtil.getRootUrl(request) + wrapWithLink.link();
        }

        // let the enum create a link for us with the desired path
        return wrapWithLink.rel().link(pathToUse);
    }

    /* POINTCUTS */

    /**
     * Defines that the method arguments must include a request parameter.
     *
     * @param request a HttpServletRequest parameter.
     */
    @SuppressWarnings("squid:UnusedPrivateMethod")
    @Pointcut("args(..,request) || args(request,..)")
    private void hasRequestParam(final HttpServletRequest request) {
    }

    /**
     * Defines that the annotation WrapWithLink must be applied.
     *
     * @param wrapWithLink a WrapWithLink annotation.
     */
    @SuppressWarnings("squid:UnusedPrivateMethod")
    @Pointcut("@annotation(wrapWithLink)")
    private void singleAnnotation(final WrapWithLink wrapWithLink) {
    }

    /**
     * Defines that the annotation WrapWithLinks must be applied.
     *
     * @param wrapWithLinks a WrapWithLinks annotation.
     */
    @SuppressWarnings("squid:UnusedPrivateMethod")
    @Pointcut("@annotation(wrapWithLinks)")
    private void multipleAnnotation(final WrapWithLinks wrapWithLinks) {
    }
}
