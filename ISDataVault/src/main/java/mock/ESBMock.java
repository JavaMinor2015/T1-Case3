package mock;

import entities.VaultEntity;
import org.springframework.stereotype.Service;
import peaseloxes.toolbox.util.RandUtil;

/**
 * Created by alex on 1/19/16.
 */
@Service
public class ESBMock {
    /**
     * Post to the ESB.
     * <p>
     * Note: 1 in 12 chance that the post fails.
     *
     * @param entity the entity to post.
     * @return true or false.
     */
    public boolean post(final VaultEntity entity) {
        // Achievement "magic numbers avoided" unlocked.
        return RandUtil.rollDice(2) != 2;
    }
}
