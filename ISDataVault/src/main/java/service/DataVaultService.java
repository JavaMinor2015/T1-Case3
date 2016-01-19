package service;

import entities.abs.PersistenceEntity;
import org.springframework.stereotype.Service;

/**
 * Created by alex on 1/19/16.
 */
@Service
public class DataVaultService {

    /**
     * Post an entity to the data vault via ESB.
     *
     * @param entity the entity to send.
     */
    public void postToVault(final PersistenceEntity entity) {

    }
}
