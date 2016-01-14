package auth.util;

import java.io.IOException;
import javax.servlet.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by alex on 1/13/16.
 */
public class AuthFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger(AuthFilter.class.getName());

    private static final String AUTH_ERROR_MSG = "Please make sure your "
            + "request has an Authorization header";
    private static final String EXPIRE_ERROR_MSG = "Token has expired";
    private static final String JWT_INVALID_MSG = "Invalid JWT token";

    @Override
    public void destroy() { /* unused */ }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { /* unused */ }

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
                         final FilterChain filterChain) throws IOException, ServletException {

    }

}
