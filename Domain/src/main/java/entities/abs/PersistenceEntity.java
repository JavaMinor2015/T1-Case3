package entities.abs;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tom on 5-1-2016.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class PersistenceEntity implements Serializable {

    private static final long serialVersionUID = 8286324917041072212L;

    @Id
    private String id;

    @Version
    private int version;
}
