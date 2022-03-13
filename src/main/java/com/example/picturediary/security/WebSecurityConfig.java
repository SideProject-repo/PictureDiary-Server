package com.example.picturediary.security;

import com.example.picturediary.security.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    private final PictureDiaryAuthenticationEntryPoint pictureDiaryAuthenticationEntryPoint;

    @Autowired
    public WebSecurityConfig(PictureDiaryAuthenticationEntryPoint pictureDiaryAuthenticationEntryPoint)
    {
        this.pictureDiaryAuthenticationEntryPoint = pictureDiaryAuthenticationEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        // authentication
        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // disable session
            .and()
            .csrf().disable() // token 인증 방식은 csrf 방어할 필요없다.
            .exceptionHandling()
                .authenticationEntryPoint(pictureDiaryAuthenticationEntryPoint)
            .and()
            // PictureDiaryCorsFilter -> UsernamePasswordAuthenticationFilter -> jwtAuthenticationFilter
            .addFilterBefore(new PictureDiaryCorsFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // authorization 은 아직 필요없다.
    }

    @Override
    public void configure(WebSecurity webSecurity)
    {
        webSecurity.ignoring().antMatchers(
            "/h2-console/**",
            "/api/sign-up",
            "/api/sign-in"
        );
    }


}
