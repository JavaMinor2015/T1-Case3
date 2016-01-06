package rest.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

/**
 * @author peaseloxes
 */
public class HateoasResponse extends ResourceSupport {

    @Getter
    private final Object content;

    /**
     * Create a response with content.
     *
     * @param content the content to wrap.
     */
    @JsonCreator
    public HateoasResponse(final Object content) {
        this.content = content;
    }
}
