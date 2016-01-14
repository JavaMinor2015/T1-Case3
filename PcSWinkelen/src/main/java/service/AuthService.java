package service;

import auth.util.AuthUtils;
import com.nimbusds.jose.JOSEException;
import entities.auth.Token;
import entities.auth.User;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repository.UserRepository;

/**
 * Implementation for Satellizer Angular Token based framework.
 * <p>
 * https://github.com/sahat/satellizer
 * <p>
 * Created by alex on 1/13/16.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/auth")
public class AuthService {

    public static final String CLIENT_ID_KEY = "client_id";
    public static final String REDIRECT_URI_KEY = "redirect_uri";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String CODE_KEY = "code";
    public static final String GRANT_TYPE_KEY = "grant_type";
    public static final String AUTH_CODE = "authorization_code";

    public static final String CONFLICT_MSG = "There is already a %s account that belongs to you";
    public static final String NOT_FOUND_MSG = "User not found";
    public static final String LOGING_ERROR_MSG = "Wrong email and/or password";
    public static final String UNLINK_ERROR_MSG = "Could not unlink %s account because it is your only sign-in method";

    @Autowired
    @Setter
    private UserRepository userRepository;

    /**
     * Initialize the user repository.
     */
    @PostConstruct
    public void init() {
        userRepository.save(
                new User("e@mail.com", hashPassword("woop"), "1234")
        );
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
            return Response.ok().entity(token).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(LOGING_ERROR_MSG).build();
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
        user.setPassword(hashPassword(user.getPassword()));
        final User savedUser = userRepository.save(user);
        final Token token = AuthUtils.createToken(request.getRemoteHost(), savedUser.getId());
        return Response.status(Response.Status.CREATED).entity(token).build();
    }

    private String hashPassword(final String plaintext) {
        return BCrypt.hashpw(plaintext, BCrypt.gensalt());
    }

    private boolean checkPassword(final String plaintext, final String hashed) {
        return BCrypt.checkpw(plaintext, hashed);
    }
}
