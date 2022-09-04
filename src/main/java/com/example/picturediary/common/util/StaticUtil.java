package com.example.picturediary.common.util;

import com.example.picturediary.security.jwt.JwtUtil;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StaticUtil
{
    public static final String BUCKET_NAME = "giljob";

    public static final String CLAIM_KEY_USER_ID = "userId";

    public static final String INFINITE_JWT_TOKEN = JwtUtil.createInfiniteToken(1L);
}
