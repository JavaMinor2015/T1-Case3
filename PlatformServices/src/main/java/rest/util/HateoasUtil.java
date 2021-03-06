package rest.util;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author peaseloxes
 */
public final class HateoasUtil {

    private static final Logger LOGGER = LogManager.getLogger(HateoasUtil.class.getName());
    public static final int DEFAULT_DEPTH = 3;

    private HateoasUtil() {
        // hide constructor
    }

    /**
     * Build an HttpEntity with a proper Hateoas response body.
     *
     * @param object the object to add as content.
     * @return a hateoas response.
     */
    public static HttpEntity<HateoasResponse> build(final Object object) {
        return new ResponseEntity<>(new HateoasResponse(object), HttpStatus.OK);
    }

    /**
     * Create a HateoasResponse with links.
     *
     * @param object the content.
     * @param links  the links.
     * @return a HateoasResponse around the object.
     */
    public static HateoasResponse toHateoas(final Object object, final Link[]... links) {
        HateoasResponse response = toHateoas(object);
        response.addAll(links);
        return response;
    }

    /**
     * Create a HateoasResponse.
     *
     * @param object the content.
     * @return a HateoasResponse around the content.
     */
    public static HateoasResponse toHateoas(final Object object) {
        return new HateoasResponse(object);
    }

    /**
     * Extracts the root url from a request.
     *
     * @param request a servlet request.
     * @return the application root url.
     */
    public static String getRootUrl(final HttpServletRequest request) {
        return request.getScheme() + "://"
                + request.getServerName()
                + ":" + request.getServerPort();
    }
}
