package com.example.picturediary.domain.user.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter@ApiModel(value = "회원 가입 응답")
public class SignUpResponse
{
    @ApiModelProperty(value = "그림 일기 access token")
    String accessToken;

    @ApiModelProperty(value = "그림 일기 refresh token")
    String refreshToken;
}
