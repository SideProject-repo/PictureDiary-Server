package com.example.picturediary.domain.user.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "로그인 요청")
public class SignInRequest
{
    @ApiModelProperty(value = "소셜 로그인 토큰")
    String socialToken;

    @ApiModelProperty(value = "소셜 로그인 타입", example = "KAKAO|GOOGLE|APPLE")
    String socialType;
}
