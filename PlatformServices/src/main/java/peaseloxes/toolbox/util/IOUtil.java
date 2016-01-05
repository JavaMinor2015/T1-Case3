package peaseloxes.toolbox.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author peaseloxes
 */
public final class IOUtil {

    /**
     * A logger instance.
     */
    private static final Logger LOGGER = LogManager.getLogger(IOUtil.class);

    /**
     * Hidden constructor.
     */
    private IOUtil() {

    }

    /**
     * Read all lines in a file and return them as a list.
     * <p>
     * Note: logs and returns empty list on IOException.
     *
     * @param path the path to the file.
     * @return a list with all lines, or an empty list.
     */
    public static List<String> readLines(final String path) {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(new File(path).toPath());
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return lines;
    }
}
