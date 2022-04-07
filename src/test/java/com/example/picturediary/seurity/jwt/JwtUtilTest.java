package com.example.picturediary.seurity.jwt;

import com.example.picturediary.security.jwt.JwtUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtUtilTest
{
    @Test
    public void createInfiniteToken()
    {
        String infiniteToken = JwtUtil.createInfiniteToken("12345");

        System.out.println(infiniteToken);
    }
}
