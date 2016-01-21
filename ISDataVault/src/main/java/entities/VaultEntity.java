package entities;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * Created by alex on 1/19/16.
 */
public class VaultEntity {
    @Getter
    private final String businessKey;
    private List<VaultEntity> containedKeys = new ArrayList<>();

    /**
     * The constructor.
     *
     * @param businessKey a business key.
     */
    public VaultEntity(final String businessKey) {
        this.businessKey = businessKey;
    }

    /**
     * Add another vault entity as a sub.
     *
     * @param entity the entity to add.
     */
    public void addSub(final VaultEntity entity) {
        containedKeys.add(entity);
    }

    @Override
    public String toString() {
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("{\"")
                .append(businessKey)
                .append("\" : [");
        for (VaultEntity containedKey : containedKeys) {
            responseBuilder.append(containedKey.toString());
        }
        responseBuilder.append("]}");
        return responseBuilder.toString();
    }
}
