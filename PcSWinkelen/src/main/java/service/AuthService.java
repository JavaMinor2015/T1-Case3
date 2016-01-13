package service;

import auth.util.AuthUtils;
import com.google.common.base.Optional;
import com.nimbusds.jose.JOSEException;
import entities.auth.Token;
import entities.auth.User;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repository.UserRepository;

/**
 * Created by alex on 1/13/16.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/auth")
public class AuthService {

    public static final String CLIENT_ID_KEY = "client_id", REDIRECT_URI_KEY = "redirect_uri",
            CLIENT_SECRET = "client_secret", CODE_KEY = "code", GRANT_TYPE_KEY = "grant_type",
            AUTH_CODE = "authorization_code";

    public static final String CONFLICT_MSG = "There is already a %s account that belongs to you",
            NOT_FOUND_MSG = "User not found", LOGING_ERROR_MSG = "Wrong email and/or password",
            UNLINK_ERROR_MSG = "Could not unlink %s account because it is your only sign-in method";

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        userRepository.save(
                new User("e@mail.com",hashPassword("woop"),"1234")
        );
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response login(@RequestBody final User user, @Context final HttpServletRequest request)
            throws JOSEException {
        final Optional<User> foundUser = Optional.of(userRepository.findByEmail(user.getEmail()));
        if (foundUser.isPresent()
                && checkPassword(user.getPassword(), foundUser.get().getPassword())) {
            final Token token = AuthUtils.createToken(request.getRemoteHost(), foundUser.get().getId());
            return Response.ok().entity(token).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(LOGING_ERROR_MSG).build();
    }

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
