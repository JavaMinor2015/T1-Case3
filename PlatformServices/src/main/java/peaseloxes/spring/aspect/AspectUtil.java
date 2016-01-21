package peaseloxes.spring.aspect;

/**
 * Created by alex on 1/20/16.
 */
public final class AspectUtil {

    private AspectUtil() {
        // hidden
    }

    /**
     * Cast and stuffs.
     *
     * @param o     my god this is bad
     * @param clazz is something this solution does not have
     * @return to sender
     */
    public static boolean imAnUntestableHorrorAndDoNotDeserveToBeInTheSameClass(final Object o, final Class clazz) {
        return clazz.isAssignableFrom(o.getClass());
    }
}
