package com.taskManagement.config;


import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.taskManagement.service.impl.UserDetailsServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component   
public class JwtAuthenticationFilter extends OncePerRequestFilter 
{

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

        // ... (existing autowired fields)
        
        @Autowired
        private UserDetailsServiceImpl userDetailsService;

        @Autowired
        private JwtUtils jwtUtils;

       
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException 
        {
            try 
            {
                final String requestTokenHeader = request.getHeader("Authorization");
                logger.info("Request Token Header: {}", requestTokenHeader);

                String username = null;
                String jwtToken = null;

                if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                    jwtToken = requestTokenHeader.substring(7);

                    try 
                    {
                        username = this.jwtUtils.extractUsername(jwtToken);
                    } 
                    catch (ExpiredJwtException e) 
                    {
                        logger.error("JWT token has expired: {}", e.getMessage());
                    } 
                    catch (Exception e) 
                    {
                        logger.error("Error extracting username from JWT token: {}", e.getMessage());
                    }
                } 
                
                else 
                {
                    logger.warn("Invalid token, does not start with 'Bearer'");
                }

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) 
                {
                    final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                    if (this.jwtUtils.validateToken(jwtToken, userDetails)) 
                    {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                } 
                
                else 
                {
                    logger.warn("Token is not valid");
                }

            } 
            
            catch (Exception e) 
            {
                logger.error("Error processing authentication: {}", e.getMessage());
            }

            filterChain.doFilter(request, response);
        }
    }


