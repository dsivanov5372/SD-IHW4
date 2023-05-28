package com.example.ordermicroservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import com.example.ordermicroservice.model.JwtAuthentication;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtFilter extends GenericFilterBean {
    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc)
            throws IOException, ServletException {

        final String token = ((HttpServletRequest) request).getHeader("Authorization");
        if (token != null && jwtProvider.validateToken(token)) {
            JwtAuthentication jwt = jwtProvider.getJwtAuthentication(token);

            jwt.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(jwt);
        }

        fc.doFilter(request, response);
    }

}