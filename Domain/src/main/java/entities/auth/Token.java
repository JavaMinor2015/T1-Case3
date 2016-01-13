package entities.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * Created by alex on 1/13/16.
 */
public class Token {
    @Getter
    private String token;

    /**
     * A constructor.
     *
     * @param token the token string.
     */
    public Token(@JsonProperty("token") String token) {
        this.token = token;
    }
}
