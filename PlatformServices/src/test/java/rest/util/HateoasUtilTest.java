package rest.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import peaseloxes.toolbox.util.testUtil.TestUtil;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by alex on 1/11/16.
 */
@RunWith(PowerMockRunner.class)
public class HateoasUtilTest {

    @Test
    public void testConstructor() throws Exception {
        assertThat(TestUtil.constructorIsPrivate(HateoasUtil.class), is(true));
    }

    @Test
    public void testBuild() throws Exception {
        assertThat(((ResponseEntity) HateoasUtil.build("woop")).getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void testToHateoas() throws Exception {
        assertThat(HateoasUtil.toHateoas("woop", new Link[]{}).getContent(), is("woop"));
    }
}
