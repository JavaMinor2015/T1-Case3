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

    public void addSub(final VaultEntity entity) {
        containedKeys.add(entity);
    }

    @Override
    public String toString() {
        String response = "{\"" + businessKey + "\" : [";
        for (VaultEntity containedKey : containedKeys) {
            response += containedKey.toString();
        }
        response += "]}";
        return response;
    }
}
