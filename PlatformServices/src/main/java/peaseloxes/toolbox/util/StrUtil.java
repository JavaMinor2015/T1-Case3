package peaseloxes.toolbox.util;

/**
 * For more options on random String generation, use Apache Commons.
 *
 * @author peaseloxes
 * @see RandUtil
 */
public final class StrUtil {

    /**
     * Default minimum for a random string.
     */
    private static final int DEFAULT_RAND_MIN = 8;

    /**
     * Default maximum for a random string.
     */
    private static final int DEFAULT_RAND_MAX = 17;

    /**
     * A list of small caps letters, for string generation purposes.
     */
    private static final String RAND_SMALL_CAPS_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";

    /**
     * A list of all caps letters, for string generation purposes.
     */
    private static final String RAND_ALL_CAPS_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Hidden constructor.
     */
    private StrUtil() {
        // and don't call me Shirley
    }

    /**
     * Replace the last occurrence in a string.
     * <p>
     * Note: if there is only one occurrence it will be replaced.
     * If there are no occurrences the original string will be returned.
     *
     * @param source      the source string
     * @param replaceThis the occurrence to replace
     * @param replaceWith the string to replace with
     * @return a string where the occurrences have been replaced
     * @see String#replaceAll(String, String)
     * @see String#replaceFirst(String, String)
     */
    public static String replaceLast(final String source, final String replaceThis, final String replaceWith) {
        final int index = source.lastIndexOf(replaceThis);
        if (index == -1) {
            return source;
        }
        return source.substring(0, index) + source.substring(index).replace(replaceThis, replaceWith);
    }

    /**
     * Fetch a random string of length between 8 and 17.
     * <p>
     * For more random string options use Apache Commons instead.
     *
     * @return a random string.
     */
    public static String getRandom() {
        return getRandom(RandUtil.rInt(DEFAULT_RAND_MIN, DEFAULT_RAND_MAX));
    }

    /**
     * Fetch a random string of a specified length.
     * <p>
     * For more random string options use Apache Commons instead.
     *
     * @param length the length of the string.
     * @return the random string.
     */
    public static String getRandom(final int length) {
        final String characters = RAND_SMALL_CAPS_CHARACTERS + RAND_ALL_CAPS_CHARACTERS;
        final StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            // rInt(max) is exclusive, so length will do just fine
            stringBuilder.append(characters.charAt(RandUtil.rInt(characters.length())));
        }
        return stringBuilder.toString();
    }

    /**
     * Fetch a random name-like string.
     * <p>
     * The names will all start with an all caps letter, followed by small caps letters.
     * <p>
     * For more random string options use Apache Commons instead.
     *
     * @param lengths the lengths of the name parts.
     * @return a random string where parts are separated by a whitespace.
     */
    public static String getRandomName(final Integer... lengths) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (Integer length : lengths) {
            // first letter
            stringBuilder.append(
                    RAND_ALL_CAPS_CHARACTERS.charAt(RandUtil.rInt(RAND_ALL_CAPS_CHARACTERS.length()))
            );

            // remaining letters
            for (int i = 1; i < length; i++) {
                stringBuilder.append(RAND_SMALL_CAPS_CHARACTERS.charAt(RandUtil.rInt(RAND_SMALL_CAPS_CHARACTERS.length())));
            }

            // whitespace
            stringBuilder.append(" ");
        }

        // trim final whitespace #yolo
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
