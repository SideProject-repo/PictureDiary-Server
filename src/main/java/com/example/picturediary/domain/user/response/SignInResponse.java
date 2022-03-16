package com.example.picturediary.domain.user.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;

@Builder
public class SignInResponse
{
    @ApiModelProperty(value = "그림 일기 access token")
    String accessToken;
}
