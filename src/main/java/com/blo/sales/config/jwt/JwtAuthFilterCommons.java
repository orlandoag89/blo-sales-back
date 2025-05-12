package com.blo.sales.config.jwt;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.blo.sales.utils.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthFilterCommons extends OncePerRequestFilter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthFilterCommons.class);
	
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        var path = request.getRequestURI();
        var header = request.getHeader("Authorization");

        if (path.startsWith("/api/v1") && header != null && header.startsWith("Bearer ")) {
            var token = header.substring(7);
            // Valida el token con JwtUtil
            if (JwtUtil.isTokenValid(token)) {
            	var username = JwtUtil.extractUsername(token);
                var role = JwtUtil.extractRole(token);
                LOGGER.info(String.format("jwt token %s de %s rol [%s]", token, username, role));
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
            			username, null, List.of(new SimpleGrantedAuthority("ROLE_" + role))
            	);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        chain.doFilter(request, response);
    }
}