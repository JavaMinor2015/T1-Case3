package peaseloxes.toolbox.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author peaseloxes
 */
public final class CurUtil {

    /**
     * Hidden constructor.
     */
    private CurUtil() {

    }

    /**
     * Convert a BigDecimal to human readable string with two decimal precision.
     * <p>
     * Uses BigDecimal.ROUND_HALF_EVEN as strategy.
     *
     * @param bigDecimal the number to convert.
     * @return the string representation.
     */
    public static String toCurrencyString(final BigDecimal bigDecimal) {
        return toCurrencyString(bigDecimal, 2);
    }

    /**
     * Convert a BigDecimal to human readable string.
     * <p>
     * Uses BigDecimal.ROUND_HALF_EVEN as strategy.
     *
     * @param bigDecimal the number to convert.
     * @param precision  the precision.
     * @return a string representation with <b>precision</b> decimals.
     */
    public static String toCurrencyString(final BigDecimal bigDecimal, final int precision) {
        return toCurrencyString(bigDecimal, precision, RoundingMode.HALF_EVEN);
    }

    /**
     * Convert a BigDecimal to human readable string.
     *
     * @param bigDecimal   the number to convert.
     * @param precision    the precision.
     * @param roundingMode the rounding mode to use.
     * @return a string representation with <b>precision</b> decimals.
     */
    public static String toCurrencyString(final BigDecimal bigDecimal, final int precision, final RoundingMode roundingMode) {
        if (bigDecimal == null) {
            throw new IllegalArgumentException("The BigDecimal provided is null.");
        }
        if (precision < 0) {
            throw new IllegalArgumentException("Precision must be positive.");
        }
        BigDecimal protectedCopy = new BigDecimal(bigDecimal.toString());
        protectedCopy = protectedCopy.setScale(precision, roundingMode);
        return protectedCopy.toString();
    }


    /**
     * Convert a Double to human readable string with two decimal precision.
     * <p>
     * Uses RoundingMode.HALF_UP as strategy.
     *
     * @param doubleValue the number to convert.
     * @return the string representation.
     */
    public static String toCurrencyString(final Double doubleValue) {
        return toCurrencyString(doubleValue, 2);
    }

    /**
     * Convert a Double to human readable string.
     * <p>
     * Uses RoundingMode.HALF_UP as strategy.
     *
     * @param doubleValue the number to convert.
     * @param precision   the precision to use.
     * @return the string representation.
     */
    public static String toCurrencyString(final Double doubleValue, final int precision) {
        return toCurrencyString(doubleValue, precision, RoundingMode.HALF_UP);
    }

    /**
     * Convert a Double to human readable string.
     *
     * @param doubleValue  the number to convert.
     * @param precision    the precision to use.
     * @param roundingMode the rounding mode to use.
     * @return the string representation.
     */
    public static String toCurrencyString(final Double doubleValue, final int precision, final RoundingMode roundingMode) {
        return toCurrencyString(new BigDecimal(doubleValue), precision, roundingMode);
    }
}
