package rest.util;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author peaseloxes
 */
public final class HateoasUtil {

    private HateoasUtil() {
        // hide constructor
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

    /**
     * Returns a set of default links given a ControllerLinkBuilder.
     * <p>
     * Contains:
     * <li>self
     * <li>next
     * <li>prev
     * <li>update
     * <li>delete
     *
     * @param builder the builder to base the links on.
     * @return an array with the default links.
     */
    public static Link[] getDefaultLinks(final ControllerLinkBuilder builder) {
        return new Link[]{
                builder.withRel("self"),
                builder.withRel("next"),
                builder.withRel("prev"),
                builder.withRel("update"),
                builder.withRel("delete")
        };
    }

}
