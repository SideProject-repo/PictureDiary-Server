package com.example.picturediary.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class PictureDiaryAuthenticationEntryPoint implements AuthenticationEntryPoint
{
    @Override
    public void commence(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException
    {
        if (authException != null && StringUtils.isNotEmpty(authException.getMessage()))
            response.sendError(HttpStatus.UNAUTHORIZED.value(), authException.getMessage());
        else
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
    }
}
