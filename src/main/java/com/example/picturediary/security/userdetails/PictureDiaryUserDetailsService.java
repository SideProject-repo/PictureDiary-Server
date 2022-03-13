package com.example.picturediary.security.userdetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class PictureDiaryUserDetailsService implements UserDetailsService
{
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        return (user, "", null);
    }
}
