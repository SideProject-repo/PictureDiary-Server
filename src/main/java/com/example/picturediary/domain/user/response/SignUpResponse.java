package com.example.picturediary.domain.user.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SignUpResponse
{
    @ApiModelProperty(value = "그림 일기 access token")
    String accessToken;
}
