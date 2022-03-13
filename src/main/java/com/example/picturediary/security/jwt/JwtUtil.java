package com.example.picturediary.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;

@UtilityClass
public class JwtUtil
{
    @Value("${spring.security.secret-key}")
    private String secretKey;

    @Value("${spring.security.access-token-valid-time}")
    private String accessTokenValidTime = "1000000000000";

    public static String createAccessToken()
    {

    }

    public static String getTokenFromHeader(HttpServletRequest httpServletRequest)
    {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        return token.replace("Bearer", "").trim();
    }

    public static String getUserIdFromSecurityContextHolder()
    {
        return SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal()
            .toString();
    }

    public static Claims parseToken(String token)
    {
        return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();
    }
}
