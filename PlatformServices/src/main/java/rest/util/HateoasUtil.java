package rest.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import rest.service.RestService;

/**
 * @author peaseloxes
 */
public final class HateoasUtil {

    private static final Logger LOGGER = LogManager.getLogger(HateoasUtil.class.getName());

    private HateoasUtil() {
        // hide constructor
    }

    /**
     * Workaround for ControllerLinkBuilder testing issues.
     *
     * @param clazz  the controller class.
     * @param params the method params.
     * @return a link with the path specified in the calling method and the provided rel.
     */
    public static Link makeLink(final Class<? extends RestService> clazz, final Object... params) {
        return makeLink(clazz, 3, "self", params);
    }

    /**
     * Workaround for ControllerLinkBuilder testing issues.
     *
     * @param clazz  the controller class.
     * @param rel    the relationship.
     * @param params the method params.
     * @return a link with the path specified in the calling method and the provided rel.
     */
    public static Link makeLink(final Class<? extends RestService> clazz, final String rel, final Object... params) {
        return makeLink(clazz, 3, rel, params);
    }

    /**
     * Use with caution.
     *
     * @param clazz  the controller class.
     * @param depth  stacktrace depth.. yes.. you heard me.
     * @param rel    link relationship.
     * @param params method params
     * @return a link with the path specified in the calling method and the provided rel.
     */
    public static Link makeLink(final Class<? extends RestService> clazz, final int depth, final String rel, final Object... params) {
        try {
            // TODO: kill it with fire
            final List<Class> classes = new ArrayList<>(params.length);
            for (Object param : params) {
                classes.add(param.getClass());
            }
            final Method method = clazz
                    .getMethod(Thread.currentThread().getStackTrace()[depth]
                            .getMethodName(), classes.toArray(new Class[classes.size()]));
            return ControllerLinkBuilder.linkTo(clazz, method, params).withRel(rel);
        } catch (NoSuchMethodException e) {
            LOGGER.warn(e);
            return new Link("method undefined", rel);
        }
        // *crying*
    }

    /**
     * Build an HttpEntity with a proper Hateoas response body.
     *
     * @param object the object to add as content.
     * @param links  the links to add.
     * @return a hateoas response.
     */
    public static HttpEntity<HateoasResponse> build(final Object object, final Link... links) {
        return new ResponseEntity<>(toHateoas(object, links), HttpStatus.OK);
    }

    /**
     * Build a Hateoas object.
     * <p>
     * Useful for nesting.
     *
     * @param object the object to add as content.
     * @param links  the links to add.
     * @return a Hateoas object.
     */
    public static HateoasResponse toHateoas(final Object object, final Link... links) {
        final HateoasResponse response = new HateoasResponse(object);
        for (Link link : links) {
            response.add(link);
        }
        return response;
    }
}
