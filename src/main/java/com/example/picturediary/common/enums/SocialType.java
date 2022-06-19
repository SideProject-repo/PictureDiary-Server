package com.example.picturediary.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocialType
{
    KAKAO("KAKAO"),
    GOOGLE("GOOGLE"),
    APPLE("APPLE");

    private final String socialTypeName;

    public static final String ALLOWABLE_VALUES = "KAKAO,GOOGLE,APPLE";
}
