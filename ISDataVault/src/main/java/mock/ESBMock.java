package mock;

import entities.VaultEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import peaseloxes.toolbox.util.RandUtil;

/**
 * Created by alex on 1/19/16.
 */
@Service
public class ESBMock {

    private static final Logger LOGGER = LogManager.getLogger(ESBMock.class.getName());

    /**
     * Post to the ESB.
     * <p>
     * Note: 1 in 12 chance that the post fails.
     *
     * @param entity the entity to post.
     * @return true or false.
     */
    public boolean post(final VaultEntity entity) {
        boolean response = RandUtil.rollDice(2) != 2;
        LOGGER.info("ESB received (mocking a " + response + " response): " + entity);
        // Achievement "magic numbers avoided" unlocked.
        return response;
    }
}
