package entities.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import entities.abs.PersistenceEntity;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by alex on 1/13/16.
 */
@Entity
@NoArgsConstructor
public class Token extends PersistenceEntity {
    @Getter
    private String token;

    @JsonIgnore
    @Getter
    @Setter
    private long timestamp;

    @JsonIgnore
    @Getter
    @Setter
    private String custId;

    @JsonIgnore
    @Getter
    @Setter
    private String userId;


    /**
     * A constructor.
     *
     * @param token the token string.
     */
    public Token(@JsonProperty("token") String token) {
        this.token = token;
    }
}
