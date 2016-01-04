package peaseloxes.toolbox.util.testUtil;

import java.lang.reflect.Constructor;

/**
 * @author peaseloxes
 */
public final class TestUtil {

    public static boolean constructorIsPrivate(final Class<?> clazz) throws Exception {
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {

            // constructor should not be accessible
            if (constructor.isAccessible()) {
                return false;
            }

            // make it accessible
            constructor.setAccessible(true);

            // because 100% :)
            if(!clazz.isInstance(constructor.newInstance())) {
                return false;
            }
        }

        // all good
        return true;
    }
}
