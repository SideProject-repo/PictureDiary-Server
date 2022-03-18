package com.example.picturediary.domain.user.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest
{
    @ApiModelProperty(value = "소셜 로그인 토큰")
    String socialToken;
}
