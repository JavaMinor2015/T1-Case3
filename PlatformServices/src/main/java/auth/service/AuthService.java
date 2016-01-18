package auth.service;

import auth.repository.TokenRepository;
import auth.repository.UserRepository;
import auth.util.AuthUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nimbusds.jose.JOSEException;
import entities.auth.Token;
import entities.auth.User;
import java.time.Instant;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

/**
 * Implementation for Satellizer Angular Token based framework.
 * <p>
 * https://github.com/sahat/satellizer
 * <p>
 * Created by alex on 1/13/16.
 */
@Service
@CrossOrigin
@RestController
@RequestMapping(value = "/auth")
public class AuthService {

    public static final String LOGIN_ERROR_MSG = "Wrong email and/or password";
    public static final long LOGIN_VALIDITY_TIME = 7200000;

    @Autowired
    @Setter
    private UserRepository userRepository;

    @Autowired
    @Setter
    private TokenRepository tokenRepository;

    /**
     * Initialize the user repository.
     */
    @PostConstruct
    public void init() {
        userRepository.save(
                new User("e@mail.com", hashPassword("woop"), "1234")
        );
        Token token = new Token("godmode=true");
        token.setTimestamp(Long.MAX_VALUE);
        tokenRepository.save(token);
    }

    /**
     * Handle a login request.
     * <p>
     * Made for the angular frontend.
     *
     * @param user    a user object.
     * @param request the request.
     * @return a token header as required by satellizer.
     * @throws JOSEException no idea what this is.
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response login(@RequestBody final User user, @Context final HttpServletRequest request)
            throws JOSEException {
        final User foundUser = userRepository.findByEmail(user.getEmail());
        if (foundUser != null
                && checkPassword(user.getPassword(), foundUser.getPassword())) {
            final Token token = AuthUtils.createToken(request.getRemoteHost(), foundUser.getId());
            token.setTimestamp(Instant.now().toEpochMilli() + LOGIN_VALIDITY_TIME);
            token.setCustId(foundUser.getCustomerId());
            tokenRepository.save(token);
            return Response.status(Response.Status.CREATED).entity(new TokenResponse(token.getToken())).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(LOGIN_ERROR_MSG).build();
    }

    /**
     * Handle a new user signup.
     *
     * @param user    the user information.
     * @param request the request.
     * @return a signup response, probably.
     * @throws JOSEException when the fit hits the shan.
     */
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public Response signup(@RequestBody final User user, @Context final HttpServletRequest request)
            throws JOSEException {
        if (user.getId() != null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid user data").build();
        }
        String oriPassword = user.getPassword();
        user.setPassword(hashPassword(user.getPassword()));
        userRepository.save(user);
        return login(new User(user.getEmail(), oriPassword, null), request);
    }

    /**
     * I wonder what this does.
     *
     * @param tokenString the token value to check for.
     * @return true if valid, false otherwise.
     */
    public boolean isAuthorized(final String tokenString) {
        Token token = tokenRepository.findByToken(tokenString);
        if (token == null) {
            return false;
        } else if (token.getTimestamp() < Instant.now().toEpochMilli()) {
            return false;
        }
        return true;
    }

    private String hashPassword(final String plaintext) {
        return BCrypt.hashpw(plaintext, BCrypt.gensalt());
    }

    private boolean checkPassword(final String plaintext, final String hashed) {
        return BCrypt.checkpw(plaintext, hashed);
    }

    private class TokenResponse {
        @Getter
        String token;

        public TokenResponse(@JsonProperty("token") String token) {
            this.token = token;
        }
    }
}
