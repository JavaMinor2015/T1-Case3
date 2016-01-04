package peaseloxes.toolbox.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import peaseloxes.toolbox.util.testUtil.TestUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author peaseloxes
 */
public class IOUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String TEST_FILENAME = "CustomTestFile.txt";

    @Test
    public void testConstructor() throws Exception {
        assertThat(TestUtil.constructorIsPrivate(IOUtil.class), is(true));
    }


    @Test
    public void testReadLines() throws Exception {
        assertThat(IOUtil.readLines(createTestFile()).size(), greaterThan(0));
        assertThat(IOUtil.readLines("").size(), is(0));
    }

    /**
     * Not in util because it's IO specific.
     *
     * @throws IOException if the fit hits the shan.
     */
    private String createTestFile() throws IOException {

        // add a file to the test folder
        testFolder.newFile(TEST_FILENAME);

        // create the full path based on generated test folder
        String fullPath = testFolder.getRoot().getAbsolutePath()
                + System.getProperty("file.separator")
                + TEST_FILENAME;

        // create some lines
        List<String> lines = new ArrayList<>();
        lines.add("This is line one");
        lines.add("This is line, two-ish");
        lines.add("This is line        three");
        lines.add("");
        lines.add("This is line five   with a tab");

        // write the lines to the test file
        Files.write(Paths.get(fullPath), lines);

        // read the lines we just wrote
        List<String> writtenLines = Files.readAllLines(Paths.get(fullPath));

        // make sure they have been written
        assertThat(lines.get(0), is(writtenLines.get(0)));

        return fullPath;
    }
}