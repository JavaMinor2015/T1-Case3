package rest.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

/**
 * @author peaseloxes
 */
@SuppressWarnings("findbugs:SS_SHOULD_BE_STATIC")
public class HateoasResponse extends ResourceSupport {

    public static final String VERSION = "6.0";
    private static final int THE_ANSWER_TO_LIFE_THE_UNIVERSE_AND_EVERYTHING = 42;

    @Getter
    @JsonProperty("jsonversion")
    private final String version = VERSION;

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
        if (!getContent().equals(other.getContent())) {
            return false;
        } else if (!getVersion().equals(other.getVersion())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return THE_ANSWER_TO_LIFE_THE_UNIVERSE_AND_EVERYTHING * version.hashCode()
                + THE_ANSWER_TO_LIFE_THE_UNIVERSE_AND_EVERYTHING * content.hashCode();
    }

    /**
     * Add arrays of links to this Hateoas object.
     *
     * @param links the arrays to add.
     */
    public void addAll(Link[]... links) {
        for (Link[] link : links) {
            this.add(link);
        }
    }
}
