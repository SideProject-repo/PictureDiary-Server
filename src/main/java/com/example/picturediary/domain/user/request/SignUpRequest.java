package com.example.picturediary.domain.user.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "회원 가입 요청")
public class SignUpRequest
{
    @ApiModelProperty(value = "소셜 회원 가입 토큰")
    String socialToken;

    @ApiModelProperty(value = "소셜 회원 가입 타입", example = "KAKAO|GOOGLE|APPLE")
    String socialType;
}
