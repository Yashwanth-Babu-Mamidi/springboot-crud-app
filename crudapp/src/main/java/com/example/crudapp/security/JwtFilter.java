package com.example.crudapp.security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.
UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.
WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.security.authentication.
UsernamePasswordAuthenticationToken;

@Component

public class JwtFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;
    private CustomUserDetailsService userDetailsService;

    public JwtFilter(JwtUtil jwtUtil,
                     CustomUserDetailsService userDetailsService){

        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override

    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)

            throws ServletException, IOException {

        String authHeader =
                request.getHeader("Authorization");

        String token = null;
        String username = null;

        if(authHeader != null &&
           authHeader.startsWith("Bearer ")){

            token = authHeader.substring(7);

            username =
                    jwtUtil.extractUsername(token);
        }

        if(username != null &&
           SecurityContextHolder
           .getContext()
           .getAuthentication() == null){

            UserDetails userDetails =
                    userDetailsService
                    .loadUserByUsername(username);

            if(jwtUtil.validateToken(
                    token,
                    userDetails.getUsername())){

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                        .buildDetails(request)
                );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request,response);
    }

}