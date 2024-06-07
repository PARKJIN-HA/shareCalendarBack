package jh.park.screenback.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;

    public JwtAuthenticationFilter(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        Cookie authCookie = cookies == null ? null : Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("AUTH-TOKEN"))
                .findAny().orElse(null);
        Authentication authentication = null;
        if (authCookie != null) {
            String token = authCookie.getValue();
            authentication = jwtUtils.verifyAndGetAuthentication(token);
            if (authentication == null) {
                String newToken = jwtUtils.refreshToken(token);
                if (newToken != null) {
                    response.addCookie(new Cookie("AUTH-TOKEN", newToken));
                }
            } else {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
