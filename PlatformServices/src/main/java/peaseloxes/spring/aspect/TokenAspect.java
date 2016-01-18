package peaseloxes.spring.aspect;

import auth.service.AuthService;
import javax.servlet.http.HttpServletRequest;
import lombok.Setter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import peaseloxes.spring.annotations.LoginRequired;
import rest.util.HateoasResponse;

/**
 * @author peaseloxes
 */
@Aspect
public class TokenAspect {

    // squid:S00112 is being raised for throwing Throwable
    // hence warning being suppressed as well as the warning
    // for unused private methods when they are clearly pointcut methods

    @Autowired
    @Setter
    private AuthService authService;

    /**
     * Checks whether a valid login token has been passed.
     *
     * @param jointPoint    the original joint point.
     * @param loginRequired the LoginRequired annotation.
     * @param request       the servlet request.
     * @return the original response if token valid, Forbidden otherwise.
     * @throws Throwable catch!
     */
    @SuppressWarnings("squid:S00112")
    @Around("hasLogin(loginRequired) && hasRequestParam(request)")
    public Object handleLinkAnnotation(final ProceedingJoinPoint jointPoint,
                                       final LoginRequired loginRequired,
                                       final HttpServletRequest request) throws Throwable {
        // if login is not required we can skip right ahead
        if (!loginRequired.value()) {
            return jointPoint.proceed();
        }
        // we should be in a method with a request parameter
        if (authService.isAuthorized(request.getHeader("Authorization"))) {
            return jointPoint.proceed();
        }
        // tell the user the bad news
        return new ResponseEntity<>(new HateoasResponse("You are not logged in or your token has expired."), HttpStatus.FORBIDDEN);
    }

    @SuppressWarnings("squid:UnusedPrivateMethod")
    @Pointcut("args(..,request) || args(request,..)")
    private void hasRequestParam(final HttpServletRequest request) {
    }

    @SuppressWarnings("squid:UnusedPrivateMethod")
    @Pointcut("@annotation(loginRequired)")
    private void hasLogin(final LoginRequired loginRequired) {
    }
}
