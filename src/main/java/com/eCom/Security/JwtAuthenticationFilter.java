package com.eCom.Security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // getToken From header
        String requestToken = request.getHeader("Authorization");
        logger.info("message {}", requestToken);

        String username = null;
        String jwtToken = null;

        if (requestToken != null && requestToken.startsWith("Bearer ")) {

            jwtToken = requestToken.substring(7);

            try {

                username = this.jwtHelper.getUsername(jwtToken);

            } catch (ExpiredJwtException e) {
                logger.info("Invalid token message", "Jwt Token expired");

            } catch (MalformedJwtException e) {
                logger.info("Invalid token message", "Invalid Jwt Token");
            } catch (IllegalArgumentException e) {
                logger.info("Invalid token message", "Unable to get token");
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // validate
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                if (this.jwtHelper.validateToken(jwtToken, userDetails)) {

                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());
                            
				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);

                } else {
                    logger.info("not validate message", "Invalid Jwt Token");
                }
            } else {
                logger.info("User message", "Username is null or auth is already there");
            }

        } else {
            logger.info("Token message {}", "token does not start with bearer");
        }

        filterChain.doFilter(request, response);
    }

}
