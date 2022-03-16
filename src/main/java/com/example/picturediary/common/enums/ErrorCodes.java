package com.example.picturediary.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodes
{
    JWT_INVALID_AUTH_TOKEN("MalformedJwtException", 1000000),
    JWT_ILLEGAL_AUTH_TOKEN("SignatureException", 1000001),
    JWT_AUTH_TOKEN_VALIDATION_ERROR("Jwt 토큰 파싱 중 에러가 발생했습니다", 1000002),
    KAKAO_SERVER_ERROR("카카오 서버 접속 중 에러가 발생했습니다", 1000003),
    JSON_PARSING_ERROR("json 파싱 중 에러가 발생했습니다", 1000004),
    NOT_SIGN_IN_USER("아직 회원가입하지 않은 회원입니다", 1000005),
    ALREADY_SIGN_IN_USER("이미 회원가입한 회원입니다", 1000006)
    ;

    private String errorMessage;
    private Integer errorCode;
}
