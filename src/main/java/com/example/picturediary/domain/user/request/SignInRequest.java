package com.example.picturediary.domain.user.request;

import com.example.picturediary.common.enums.SocialType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ApiModel(value = "로그인 요청")
public class SignInRequest
{
    @NotNull
    @ApiModelProperty(value = "소셜 로그인 토큰")
    private String socialToken;

    @NotNull
    @Pattern(regexp = SocialType.REG_EXP)
    @ApiModelProperty(value = "소셜 로그인 타입", example = SocialType.ALLOWABLE_VALUES)
    private String socialType;
}
