package peaseloxes.toolbox.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import peaseloxes.toolbox.util.testUtil.TestUtil;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author peaseloxes
 */
public class RandUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testConstructor() throws Exception {
        assertThat(TestUtil.constructorIsPrivate(RandUtil.class), is(true));
    }

    @Test
    public void testRInt() throws Exception {
        assertThat(RandUtil.rInt(), is(both(greaterThanOrEqualTo(Integer.MIN_VALUE)).and(lessThanOrEqualTo(Integer.MAX_VALUE))));
    }

    @Test
    public void testRInt1Valid() throws Exception {
        assertThat(RandUtil.rInt(5), is(both(lessThan(5)).and(greaterThanOrEqualTo(0))));
        assertThat(RandUtil.rInt(Integer.MAX_VALUE), is(both(lessThan(Integer.MAX_VALUE)).and(greaterThanOrEqualTo(0))));
    }

    @Test
    public void testRInt1Invalid1() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        RandUtil.rInt(0);
    }

    @Test
    public void testRInt1Invalid2() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        RandUtil.rInt(-1);
    }

    @Test
    public void testRInt2Valid1() throws Exception {
        assertThat(RandUtil.rInt(5, 10), is(both(greaterThanOrEqualTo(5)).and(lessThanOrEqualTo(10))));
    }

    @Test
    public void testRInt2Valid2() throws Exception {
        assertThat(RandUtil.rInt(-10, 10), is(both(greaterThanOrEqualTo(0)).and(lessThanOrEqualTo(10))));
    }

    @Test
    public void testRInt2Invalid1() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        RandUtil.rInt(-20, -10);
    }

    @Test
    public void testRInt2Invalid2() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        RandUtil.rInt(-20, -10);
    }

    @Test
    public void testRInt2Invalid3() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        RandUtil.rInt(10, 9);
    }

    @Test
    public void testRInt2Invalid4() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        RandUtil.rInt(10, 10);
    }

    @Test
    public void testFiftyFifty() throws Exception {
        // no exceptions be thrown
        RandUtil.fiftyFifty();
    }

    @Test
    public void testRollDie() throws Exception {
        assertThat(RandUtil.rollDie(), is(both(greaterThanOrEqualTo(1)).and(lessThanOrEqualTo(6))));
    }

    @Test
    public void testRollDice() throws Exception {
        assertThat(RandUtil.rollDice(4), is(both(greaterThanOrEqualTo(4)).and(lessThanOrEqualTo(24))));
        assertThat(RandUtil.rollDice(0), is(0));
        thrown.expect(IllegalArgumentException.class);
        RandUtil.rollDice(-1);
    }
}