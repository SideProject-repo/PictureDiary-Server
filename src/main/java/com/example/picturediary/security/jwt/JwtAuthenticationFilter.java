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

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        FilterChain chain) throws IOException
    {
        try {
            String token = JwtUtil.getTokenFromHeader((HttpServletRequest) request);

            PictureDiaryUserDetails userDetails
                = (PictureDiaryUserDetails) pictureDiaryUserDetailsService.loadUserByUsername(token);

            SecurityContextHolder.getContext().setAuthentication((Authentication) userDetails);

        } catch (MalformedJwtException e)
        {
            ResponseUtil.doResponse((HttpServletResponse) response, ErrorCodes.JWT_INVALID_AUTH_TOKEN);
        }
        catch (SignatureException e)
        {
            ResponseUtil.doResponse((HttpServletResponse) response, ErrorCodes.JWT_ILLEGAL_AUTH_TOKEN);
        }
        catch (Exception e)
        {
            ResponseUtil.doResponse((HttpServletResponse) response, ErrorCodes.JWT_AUTH_TOKEN_VALIDATION_ERROR);
        }
    }
}
