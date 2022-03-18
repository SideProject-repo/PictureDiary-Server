package com.example.picturediary.security.userdetails;

import com.example.picturediary.security.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PictureDiaryUserDetailsService implements UserDetailsService
{
    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException
    {
        return loadUserByToken(token);
    }

    private PictureDiaryUserDetails loadUserByToken(String token)
    {
        Claims claims = JwtUtil.parseToken(token);
        return JwtUtil.getUserDetailsFromClaims(claims);
    }
}
