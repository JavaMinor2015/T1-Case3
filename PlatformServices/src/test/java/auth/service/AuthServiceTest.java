package auth.service;

import auth.repository.TokenRepository;
import auth.repository.UserRepository;
import entities.auth.Token;
import entities.auth.User;
import java.time.Instant;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mockito;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

/**
 * @author peaseloxes
 */
public class AuthServiceTest {

    private AuthService service;
    private UserRepository mockUserRepository;
    private TokenRepository mockTokenRepository;
    private HttpServletRequest mockServletRequest;

    @Before
    public void setUp() throws Exception {
        service = new AuthService();
        mockUserRepository = Mockito.mock(UserRepository.class);
        mockTokenRepository = Mockito.mock(TokenRepository.class);
        service.setTokenRepository(mockTokenRepository);
        service.setUserRepository(mockUserRepository);
        mockServletRequest = Mockito.mock(HttpServletRequest.class);
    }

    @Test
    public void testInit() throws Exception {
        Mockito.when(mockTokenRepository.save(any(Token.class))).thenReturn(new Token());
        Mockito.when(mockUserRepository.save(any(User.class))).thenReturn(new User());
        // no errors
        service.init();
    }

    @Test
    public void testLogin() throws Exception {
        String existingEmail = "email@company.com";
        String existingPassword = "woop";
        String salt = BCrypt.gensalt();

        User validRequest = new User();
        validRequest.setPassword(existingPassword);
        validRequest.setEmail(existingEmail);

        User invalidRequest = new User();
        invalidRequest.setPassword("asd");
        invalidRequest.setEmail(existingEmail);

        User invalidRequest2 = new User();
        invalidRequest2.setPassword("asd");
        invalidRequest2.setEmail("df@company.com");

        User existingUser = new User();
        existingUser.setId("1");
        existingUser.setCustomerId(existingPassword);
        existingUser.setEmail(existingEmail);
        existingUser.setPassword(BCrypt.hashpw(existingPassword, salt));

        Mockito.when(mockServletRequest.getRemoteHost()).thenReturn("localhost");
        Mockito.when(mockUserRepository.findByEmail(eq(existingEmail))).thenReturn(existingUser);
        assertThat(service.login(validRequest, mockServletRequest).getStatusInfo(),
                is(Response.Status.CREATED));
        assertThat(service.login(invalidRequest, mockServletRequest).getStatusInfo(),
                is(Response.Status.UNAUTHORIZED));
        assertThat(service.login(invalidRequest2, mockServletRequest).getStatusInfo(),
                is(Response.Status.UNAUTHORIZED));
    }

    @Test
    public void testSignup() throws Exception {
        String existingEmail = "email@company.com";
        String existingPassword = "woop";
        String salt = BCrypt.gensalt();

        User existingUser = new User();
        existingUser.setId("1");
        existingUser.setCustomerId("1");
        existingUser.setEmail(existingEmail);
        existingUser.setPassword(BCrypt.hashpw(existingPassword, salt));

        Mockito.when(mockServletRequest.getRemoteHost()).thenReturn("localhost");
        Mockito.when(mockUserRepository.findByEmail(eq(existingEmail))).thenReturn(existingUser);
        Mockito.when(mockUserRepository.save(eq(existingUser))).thenReturn(existingUser);

        assertThat(service.signup(existingUser, mockServletRequest).getStatusInfo(), is(Response.Status.BAD_REQUEST));
        existingUser.setId(null);
        assertThat(service.signup(existingUser, mockServletRequest).getStatusInfo(), is(Response.Status.CREATED));
    }

    @Test
    public void testIsAuthorized() throws Exception {

        assertThat(service.isAuthorized(null), is(false));

        Token validToken = new Token("1");
        validToken.setTimestamp(Instant.now().toEpochMilli() + 500000);

        Token invalidToken = new Token("1");
        invalidToken.setTimestamp(Instant.now().toEpochMilli() - 50000);

        Mockito.when(mockTokenRepository.findByToken(eq("1"))).thenReturn(validToken);
        assertThat(service.isAuthorized(validToken.getToken()), is(true));

        Mockito.when(mockTokenRepository.findByToken(eq("1"))).thenReturn(invalidToken);
        assertThat(service.isAuthorized(invalidToken.getToken()), is(false));
    }
}