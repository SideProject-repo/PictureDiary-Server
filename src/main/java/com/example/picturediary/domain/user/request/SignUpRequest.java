package com.example.picturediary.domain.user.request;

import com.example.picturediary.common.enums.SocialType;
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
    private String socialToken;

    @ApiModelProperty(value = "소셜 회원 가입 타입", example = SocialType.ALLOWABLE_VALUES)
    private String socialType;
}
