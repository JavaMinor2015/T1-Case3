package entities;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by alex on 1/19/16.
 */
public class VaultEntityTest {

    @Test
    public void testToString() throws Exception {
        VaultEntity entity = new VaultEntity("woop");
        entity.addSub(new VaultEntity("woop2"));
        entity.addSub(new VaultEntity("woop2.1"));
        VaultEntity entity2 = new VaultEntity("woop3");
        entity2.addSub(new VaultEntity("woop3.1"));
        entity2.addSub(new VaultEntity("woop3.2"));
        entity2.addSub(new VaultEntity("woop3.3"));
        entity.addSub(entity2);
        assertThat(entity.toString(),
                is("{\"woop\" : [{\"woop2\" : []}{\"woop2.1\" : []}{\"woop3\" : [{\"woop3.1\" : []}{\"woop3.2\" : []}{\"woop3.3\" : []}]}]}"));
    }
}