package rest.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.hateoas.Link;
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
        assertThat(HateoasUtil.build("Test", new Link("Foo", "Bar")).getBody().getContent(), is("Test"));
    }

    @Test
    public void testToHateoas() throws Exception {
        assertThat(HateoasUtil.toHateoas("Test", new Link("Foo", "Bar")).getContent(), is("Test"));
    }
}