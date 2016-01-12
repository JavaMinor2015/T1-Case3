package rest.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

/**
 * @author peaseloxes
 */
@SuppressWarnings("findbugs:SS_SHOULD_BE_STATIC")
public class HateoasResponse extends ResourceSupport {

    @Getter
    private final String version = "5.0";

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

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof HateoasResponse)) {
            return false;
        }
        HateoasResponse other = (HateoasResponse) obj;
               

        return true;

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
