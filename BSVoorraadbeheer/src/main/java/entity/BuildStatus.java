package entity;

import entities.abs.PersistenceEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by alex on 1/12/16.
 */
public class BuildStatus extends PersistenceEntity {
    private static final long serialVersionUID = -3980878381366030377L;

    @Getter
    @Setter
    private boolean ready = false;

    /**
     * Create a build status for an id.
     *
     * @param id the build id.
     */
    public BuildStatus(final String id) {
        this.setId(id);
    }
}
