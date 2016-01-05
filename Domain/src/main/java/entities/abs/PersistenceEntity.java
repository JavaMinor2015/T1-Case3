package entities.abs;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tom on 5-1-2016.
 */
@Getter
@Setter
public abstract class PersistenceEntity {
    @Id
    @GeneratedValue
    private long id;
}
