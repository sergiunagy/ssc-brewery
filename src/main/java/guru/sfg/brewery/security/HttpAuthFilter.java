package guru.sfg.brewery.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*https://medium.com/@grosu.andrei.nicolae/spring-security-authentification-with-credentials-in-request-payload-5378ba1c81b6*/

@Slf4j
public class HttpAuthFilter extends AbstractBaseAuthFilter {


    public HttpAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    protected String getUserName(HttpServletRequest request) {

        return request.getParameter("apiKey");
    }

    protected String getUserPass(HttpServletRequest request) {

        return request.getParameter("apiSecret");
    }

}
