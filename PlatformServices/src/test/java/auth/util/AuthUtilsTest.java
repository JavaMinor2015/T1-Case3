package auth.util;

import entities.auth.Token;
import org.junit.Test;
import peaseloxes.toolbox.util.testUtil.TestUtil;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

/**
 * Created by alex on 1/14/16.
 */
public class AuthUtilsTest {

    @Test
    public void testConstructor() throws Exception {
        TestUtil.constructorIsPrivate(AuthUtils.class);
    }

    @Test
    public void testCreateToken() throws Exception {
        Token response = AuthUtils.createToken("127.0.0.1", "1");
        assertThat(response.getToken(), startsWith("eyJhbGciOiJIUzI1NiJ9"));
    }

    @Test
    public void testGetSerializedToken() throws Exception {
        assertThat(AuthUtils.getSerializedToken("foo bar"), is("bar"));
    }
}