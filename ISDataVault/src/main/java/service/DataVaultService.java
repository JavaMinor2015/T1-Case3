package service;

import entities.VaultEntity;
import entities.abs.PersistenceEntity;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import lombok.Setter;
import mock.ESBMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import util.EntityConverter;

/**
 * Created by alex on 1/19/16.
 */
@Service
public class DataVaultService {

    public static final int DELAY = 5000;

    @Autowired
    @Setter
    private ESBMock esb;

    private BlockingQueue<VaultEntity> blockingQueue = new LinkedBlockingQueue<>();

    /**
     * Post an entity to the data vault via ESB.
     *
     * @param entity the entity to send.
     */
    @Async
    public void postToVault(final PersistenceEntity entity) {
        VaultEntity vaultEntity = EntityConverter.convert(entity);
        if (!esb.post(vaultEntity)) {
            blockingQueue.add(vaultEntity);
        }
    }

    /**
     * Retry all the unsuccessful posts.
     */
    @Scheduled(fixedDelay = DELAY)
    public void retry() {
        if (!blockingQueue.isEmpty()) {
            Iterator<VaultEntity> it = blockingQueue.iterator();
            while (it.hasNext()) {
                if (esb.post(it.next())) {
                    it.remove();
                }
            }
        }
    }
}
