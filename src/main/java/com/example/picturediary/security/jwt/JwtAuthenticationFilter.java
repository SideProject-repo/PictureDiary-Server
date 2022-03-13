package com.example.picturediary.security.jwt;

import com.example.picturediary.security.userdetails.PictureDiaryUserDetailsService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtAuthenticationFilter extends GenericFilterBean
{
    private final PictureDiaryUserDetailsService pictureDiaryUserDetailsService;

    @Autowired
    public JwtAuthenticationFilter(PictureDiaryUserDetailsService pictureDiaryUserDetailsService)
    {
        this.pictureDiaryUserDetailsService = pictureDiaryUserDetailsService;
    }

    @Override
    public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain) throws IOException, ServletException
    {
        try {
            String token = JwtUtil.getTokenFromHeader((HttpServletRequest) request);
            Claims claims = JwtUtil.parseToken(token);
            SecurityContextHolder.getContext().setAuthentication(
                pictureDiaryUserDetailsService.loadUserByUsername(claims.getSubject())
            );
        }
    }
}
