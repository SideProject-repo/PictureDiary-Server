package com.example.picturediary.domain.user.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@ApiModel(value = "로그인 응답")
public class SignInResponse
{
    @ApiModelProperty(value = "그림 일기 access token")
    String accessToken;

    @ApiModelProperty(value = "그림 일기 refresh token")
    String refreshToken;
}
