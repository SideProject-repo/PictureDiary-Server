package com.example.picturediary.seurity.jwt;

import com.example.picturediary.common.util.StaticUtil;
import com.example.picturediary.security.jwt.JwtUtil;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtUtilTest
{
    @Test
    @DisplayName("무기한 JWT 토큰 발급 받고 파싱 성공")
    public void createInfiniteToken()
    {
        String infiniteToken = JwtUtil.createInfiniteToken(1L);

        Integer userId = (Integer) JwtUtil.parseToken(infiniteToken).get(StaticUtil.CLAIM_KEY_USER_ID);

        Assertions.assertEquals(userId, 1);
    }
}
