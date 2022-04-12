package com.example.picturediary.common.response;

import lombok.Getter;

@Getter
public class CommonResponse
{
    private final String responseMessage;

    public CommonResponse(String responseMessage)
    {
        this.responseMessage = responseMessage;
    }
}
