package com.example.picturediary.security.jwt;

import com.example.picturediary.common.enums.ErrorCodes;
import com.example.picturediary.common.util.ResponseUtil;
import com.example.picturediary.security.userdetails.PictureDiaryUserDetails;
import com.example.picturediary.security.userdetails.PictureDiaryUserDetailsService;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter
{
    private final PictureDiaryUserDetailsService pictureDiaryUserDetailsService;

    @Autowired
    public JwtAuthenticationFilter(PictureDiaryUserDetailsService pictureDiaryUserDetailsService)
    {
        this.pictureDiaryUserDetailsService = pictureDiaryUserDetailsService;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException
    {
        try {
            String token = JwtUtil.getTokenFromHeader(request);

            PictureDiaryUserDetails userDetails
                = (PictureDiaryUserDetails) pictureDiaryUserDetailsService.loadUserByUsername(token);

            SecurityContextHolder.getContext().setAuthentication((Authentication) userDetails);

            filterChain.doFilter(request, response);
        } catch (MalformedJwtException e)
        {
            ResponseUtil.doResponse(response, ErrorCodes.JWT_INVALID_AUTH_TOKEN);
        }
        catch (SignatureException e)
        {
            ResponseUtil.doResponse(response, ErrorCodes.JWT_ILLEGAL_AUTH_TOKEN);
        }
        catch (Exception e)
        {
            ResponseUtil.doResponse(response, ErrorCodes.JWT_AUTH_TOKEN_VALIDATION_ERROR);
        }
    }
}
