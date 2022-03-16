package com.example.picturediary.security.jwt;

import com.example.picturediary.security.userdetails.PictureDiaryUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@UtilityClass
public class JwtUtil
{
    @Value("${spring.security.secret-key}")
    private String secretKey;

    @Value("${spring.security.access-token-valid-time}")
    private String accessTokenValidTime = "1000000000000";

    private static final String CLAIM_KEY_USER = "userId";

    public static String createAccessToken(String userId)
    {
        Date date = new Date();

        return Jwts.builder()
            .setIssuedAt(date)
            .setExpiration(new Date(date.getTime() + Long.parseLong(accessTokenValidTime)))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .claim(CLAIM_KEY_USER, userId)
            .compact();
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

    public static PictureDiaryUserDetails getUserDetailsFromClaims(Claims claims)
    {
        return new PictureDiaryUserDetails(claims.get(CLAIM_KEY_USER));
    }
}
