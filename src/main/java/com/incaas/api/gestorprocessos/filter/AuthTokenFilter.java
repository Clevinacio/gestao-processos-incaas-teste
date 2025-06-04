package com.incaas.api.gestorprocessos.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthTokenFilter extends OncePerRequestFilter{

    private final String authToken;

    public AuthTokenFilter(String authToken) {
        this.authToken = authToken;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Acesso não autorizado: Token Bearer ausente ou inválido.");
            return;
        }
        String token = authorizationHeader.substring(7);
        if (!authToken.equals(token)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Acesso proibido: Token inválido.");
            return;
        }
        filterChain.doFilter(request, response);
    }

}
