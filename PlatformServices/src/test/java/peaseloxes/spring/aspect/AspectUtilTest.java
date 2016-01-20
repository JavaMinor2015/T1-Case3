package peaseloxes.spring.aspect;

import org.junit.Test;
import peaseloxes.toolbox.util.testUtil.TestUtil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by alex on 1/20/16.
 */
public class AspectUtilTest {

    @Test
    public void testConstructor() throws Exception {
        assertThat(TestUtil.constructorIsPrivate(AspectUtil.class), is(true));
    }

    @Test
    public void testImAnUntestableHorrorAndDoNotDeserveToBeInTheSameClass() throws Exception {
        assertThat(AspectUtil.imAnUntestableHorrorAndDoNotDeserveToBeInTheSameClass(
                new Object(),
                Object.class
        ), is(true));
    }
}