package com.indusnet.ums.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class JWTUtils {

    private JWTUtils() {
    }

    private final SecretKey secretKey = Jwts.SIG.HS256.key().build();
    private final String ISSUER = "com.DemoProject.cmp";


    public boolean validateToken(String jwtToken) {
        return parseToken(jwtToken).isPresent();//means token is valid if we are passing
    }

    private Optional<Claims> parseToken(String jwtToken) {
        var jwtParser = Jwts.parser()
                .verifyWith(secretKey)
                .build();
        try {
            return Optional.of(jwtParser.parseSignedClaims(jwtToken)
                    .getPayload());//USERNAME INFORMATION
        } catch (JwtException | IllegalArgumentException e) {
            log.error("jwt exception occurred");


        }
        return Optional.empty();
    }

    public Optional<String> getUsernameFromToken(String jwtToken) {
        var claimsOptional = parseToken(jwtToken);
        return claimsOptional.map(Claims::getSubject);  //username
    }

    public String generateToken(String email) {
        var currentDate = new Date();
        var jwtExpirationInMinutes = 10;
        var expiration = DateUtils.addMinutes(currentDate, jwtExpirationInMinutes);
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .issuer(ISSUER)
                .subject(email)
                .signWith(secretKey)
                .issuedAt(currentDate)
                .expiration(expiration)
                .compact();

    }

//    public String generateRefreshToken(HashMap<String, Object> claims, String username) {
//        var currentDate = new Date();
//        var jwtExpirationInMinutes = 10;
//        var expiration = DateUtils.addMinutes(currentDate, jwtExpirationInMinutes);
//        return Jwts.builder()
//                .id(UUID.randomUUID().toString())
//                .issuer(ISSUER)
//                .subject(username)
//                .signWith(secretKey)
//                .issuedAt(currentDate)
//                .expiration(expiration)
//                .compact();
    }


