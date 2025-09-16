package com.multiGenesys.security.filter;

import com.multiGenesys.security.JwtUtils;
import com.multiGenesys.security.exception.ExpiredJwtTokenException;
import com.multiGenesys.security.impl.BookingUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private BookingUserDetails bookingUserDetails;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //System.out.println("request " + request.getRequestURI());
        String jwt = request.getHeader("Authorization");
        if ((jwt != null && jwt.startsWith("Bearer "))){
            jwt = jwt.substring(7).trim();

            // Validate the token
            if (jwtUtils.validateJwtToken(jwt)) {
                try {
                    String username = jwtUtils.getUsernameFromToken(jwt);
                    logger.info("Filter username " + username);
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    UserDetails userDetails = bookingUserDetails.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication =
                               new UsernamePasswordAuthenticationToken(
                                          userDetails,
                                          null,
                                          userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    context.setAuthentication(authentication);
                    SecurityContextHolder.setContext(context);
                }
                catch (ExpiredJwtTokenException ex){
                    logger.error("JWT token is expired: {}"+ ex.getMessage());
                    throw new ExpiredJwtTokenException("Jwt token is expired");
                }
                catch (Exception e) {
                    logger.error("Cannot set user authentication: {}", e);
                }
            }
        }
        filterChain.doFilter(request, response);
    }


}