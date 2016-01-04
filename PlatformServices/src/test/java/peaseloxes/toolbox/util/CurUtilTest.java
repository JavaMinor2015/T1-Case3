package peaseloxes.toolbox.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import peaseloxes.toolbox.util.testUtil.TestUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author peaseloxes
 */
public class CurUtilTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testConstructor() throws Exception {
        assertThat(TestUtil.constructorIsPrivate(CurUtil.class), is(true));
    }

    @Test
    public void testBigDecimalToCurrencyString() throws Exception {
        final String response = CurUtil.toCurrencyString(new BigDecimal("5.1234567"));
        assertThat(response, is("5.12"));
    }

    @Test
    public void testBigDecimalToCurrencyString1() throws Exception {
        final String response = CurUtil.toCurrencyString(new BigDecimal("5.1234567"), 3);
        assertThat(response, is("5.123"));
    }

    @Test
    public void testBigDecimalToCurrencyString2() throws Exception {
        final String response = CurUtil.toCurrencyString(new BigDecimal("5.1"), 0, RoundingMode.DOWN);
        assertThat(response, is("5"));
    }

    @Test
    public void testBigDecimalToCurrencyStringInvalid() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        final BigDecimal nullBigDecimal = null;
        CurUtil.toCurrencyString(nullBigDecimal);
    }

    @Test
    public void testBigDecimalToCurrencyStringInvalid2() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        CurUtil.toCurrencyString(new BigDecimal(2), -1);
    }

    @Test
    public void testDoubleToCurrencyString() throws Exception {
        final String response = CurUtil.toCurrencyString(5.123d);
        assertThat(response, is("5.12"));
        // note: rest of the doubles are covered by extension of BigDecimal
    }

}