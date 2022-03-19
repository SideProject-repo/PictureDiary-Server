package com.example.picturediary.security;

import com.example.picturediary.security.jwt.JwtAuthenticationFilter;
import com.example.picturediary.security.userdetails.PictureDiaryUserDetailsService;
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
    private final PictureDiaryUserDetailsService pictureDiaryUserDetailsService;

    @Autowired
    public WebSecurityConfig(
        PictureDiaryAuthenticationEntryPoint pictureDiaryAuthenticationEntryPoint,
        PictureDiaryUserDetailsService pictureDiaryUserDetailsService)
    {
        this.pictureDiaryAuthenticationEntryPoint = pictureDiaryAuthenticationEntryPoint;
        this.pictureDiaryUserDetailsService = pictureDiaryUserDetailsService;
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        web.ignoring()
            .antMatchers(
                "/h2-console/**",
                "/swagger-ui/**",
                "/v2/api-docs",
                "/configuration/**",
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/webjars/**",
                "/swagger/**",
                "/favicon.ico",
                "/dont-pass-filter",
                "/auth/sign-up",
                "/auth/sign-in"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // disable session

            .and()
            .csrf().disable() // token 인증 방식은 csrf 방어할 필요없다.
            .exceptionHandling()
                .authenticationEntryPoint(pictureDiaryAuthenticationEntryPoint)

            // PictureDiaryCorsFilter -> UsernamePasswordAuthenticationFilter -> jwtAuthenticationFilter
            .and()
            .addFilterBefore(new PictureDiaryCorsFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new JwtAuthenticationFilter(pictureDiaryUserDetailsService), UsernamePasswordAuthenticationFilter.class);
    }
}
