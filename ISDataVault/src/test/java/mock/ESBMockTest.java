package mock;

import entities.VaultEntity;
import org.junit.Test;

/**
 * Created by alex on 1/21/16.
 */
public class ESBMockTest {

    @Test
    public void testPost() throws Exception {
        // assert randomness? I think not
        int counter = 100;
        for (int i = 0; i < counter; i++) {
            new ESBMock().post(new VaultEntity("woop"));
        }
    }
}