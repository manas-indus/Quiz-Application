package com.indusnet.ums.Security;

import com.indusnet.ums.util.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
@Component
@RequiredArgsConstructor
public class JWTAuthentication extends OncePerRequestFilter {

        private final UserDetailsService userDetailsService;
        private final JWTUtils jwtUtils;

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            //fetch token from request
            var jwtTokenOptional = getTokenFromRequest(request);
            //Validate JwtTOKEN -> JWT Utils
            jwtTokenOptional.ifPresent(jwtToken ->{
                if(jwtUtils.validateToken(jwtToken)){
                    //get username from jwtToken
                    var usernameOptional = jwtUtils.getUsernameFromToken(jwtToken);
                    usernameOptional.ifPresent(email ->{
                        //fetch user detail with the help of username
                        var userDetails = userDetailsService.loadUserByUsername(email);
                        //create Authentication Token
                        var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        //set authentication token as security context
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    });
                }



            });

            //pass request nd response to next filter
            filterChain.doFilter(request, response);


        }
        private Optional<String> getTokenFromRequest(HttpServletRequest request){
            //Extract authentication header
            var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            //bearer<JWT TOKEN>
            if(StringUtils.hasText(authHeader)&& authHeader.startsWith("Bearer ")) {
                return Optional.of(authHeader.substring(7));
            }
            return Optional.empty();

        }
    }


