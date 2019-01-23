package univ.paris13.lee.app.controller.filter;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) request;
        final String accessToken = (String) req.getSession().getAttribute("access_token");
        if (accessToken != null) {
            // add Authorization header
            next.doFilter(wrap(req, "Bearer " + accessToken), response);
            return;
        }
        next.doFilter(request, response);
    }

    private static HttpServletRequest wrap(HttpServletRequest request, String bearerToken) {
        return new HttpServletRequestWrapper(request) {
            @Override
            public String getHeader(String name) {
                if (HttpHeaders.AUTHORIZATION.equals(name)) {
                    return bearerToken;
                }
                return super.getHeader(name);
            }

            @Override
            public Enumeration<String> getHeaders(String name) {
                if (HttpHeaders.AUTHORIZATION.equals(name)) {
                    return Collections.enumeration(Collections.singleton(bearerToken));
                }
                return super.getHeaders(name);
            }

            @Override
            public Enumeration<String> getHeaderNames() {
                final List<String> headerNames = new ArrayList<>(Collections.list(super.getHeaderNames()));
                headerNames.add(HttpHeaders.AUTHORIZATION);
                return Collections.enumeration(headerNames);
            }
        };
    }
}
