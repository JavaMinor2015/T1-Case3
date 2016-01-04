package peaseloxes.toolbox.util;

import java.security.SecureRandom;

/**
 * @author peaseloxes
 */
public final class RandUtil {

    /**
     * Secure random instance.
     */
    private static final SecureRandom RAND = new SecureRandom();

    /**
     * How many sides a die has.
     */
    private static final int DIE_SIDES = 6;

    /**
     * Hidden constructor.
     */
    private RandUtil() {

    }


    /**
     * Returns a random integer.
     *
     * @return a random integer.
     * @see SecureRandom#nextInt()
     */
    public static Integer rInt() {
        return RAND.nextInt();
    }

    /**
     * Returns a random integer between 0 (inclusive) and max (exclusive).
     *
     * @param max the upper limit.
     * @return a random integer.
     */
    public static Integer rInt(final int max) {
        return RAND.nextInt(max);
    }

    /**
     * Returns a random integer between min and max inclusive.
     *
     * @param min the lower bound
     * @param max the upper bound
     * @return a random integer
     */
    public static Integer rInt(final int min, final int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Max bound should be greater than min bound.");
        }
        int result;
        do {
            result = RAND.nextInt(max);
        } while (result < min);
        return result;
    }

    /**
     * Returns true in approximately 50% of all cases.
     *
     * @return 50% chance on true, 50% chance on false
     */
    public static boolean fiftyFifty() {
        return RAND.nextBoolean();
    }

    /**
     * Roll a die.
     * <p>
     * Essentially rInt(1,6).
     *
     * @return random number between 1 and 6, inclusive.
     */
    public static Integer rollDie() {
        return rInt(1, DIE_SIDES);
    }

    /**
     * Roll dice and return the sum of their rolls.
     *
     * @param amount the amount of dice to roll.
     * @return the sum of all rolls.
     */
    public static Integer rollDice(final int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount of dice must be positive.");
        }
        int result = 0;
        for (int i = 0; i < amount; i++) {
            result += rInt(1, DIE_SIDES);
        }
        return result;
    }
}
