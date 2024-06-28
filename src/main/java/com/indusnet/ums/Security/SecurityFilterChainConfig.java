package com.indusnet.ums.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration

public class SecurityFilterChainConfig {


    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final JWTAuthentication jwtAuthentication;



    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        {

            httpSecurity.csrf(AbstractHttpConfigurer::disable);
            httpSecurity.authorizeHttpRequests(
                    requestMatcher ->
                            requestMatcher.requestMatchers("/api/ums/**","/quizzes","/quizzes/{id}","/question","/question/{id}","/results").permitAll()
                                    .anyRequest().authenticated()

            );
            //Authentication Entry point ->exception handling
            httpSecurity.exceptionHandling(
                    httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(authenticationEntryPoint)

            );
            //Session Policy
            httpSecurity.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
            //Add a jwt Authentication filter
            httpSecurity.addFilterBefore(jwtAuthentication, UsernamePasswordAuthenticationFilter.class);
            return httpSecurity.build();
        }

    }
}

