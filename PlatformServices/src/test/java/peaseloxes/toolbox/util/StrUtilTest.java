package peaseloxes.toolbox.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import peaseloxes.toolbox.util.testUtil.TestUtil;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author peaseloxes
 */
public class StrUtilTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testConstructor() throws Exception {
        assertThat(TestUtil.constructorIsPrivate(StrUtil.class), is(true));
    }

    @Test
    public void testReplaceLast() throws Exception {
        assertThat(StrUtil.replaceLast("aaabbbbaaaccccaaa", "aaa", "zzz"), is("aaabbbbaaacccczzz"));
        assertThat(StrUtil.replaceLast("aaabbbbccc", "aaa", "zzz"), is("zzzbbbbccc"));
        assertThat(StrUtil.replaceLast("aaabbbbccc", "xxx", "zzz"), is("aaabbbbccc"));
    }

    @Test
    public void testGetRandom() throws Exception {
        // between 8 and 17
        assertThat(StrUtil.getRandom().length(), is(both(lessThanOrEqualTo(17)).and(greaterThanOrEqualTo(8))));
    }

    @Test
    public void testGetRandom1() throws Exception {
        assertThat(StrUtil.getRandom(8).length(), is(8));
    }

    @Test
    public void testGetRandomName() throws Exception {
        //length -> 5 + 1 + 2 + 1 + 4
        assertThat(StrUtil.getRandomName(5, 2, 4).length(), is(13));

        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String[] nameParts = StrUtil.getRandomName(5, 2, 4).split(" ");
        final Integer[] sizes = new Integer[]{5, 2, 4};
        for (int i = 0; i < nameParts.length; i++) {
            assertThat(nameParts[i].length(), is(sizes[i]));
            assertThat(chars.contains(String.valueOf(nameParts[i].charAt(0))), is(true));
        }
    }
}