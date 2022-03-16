package com.example.picturediary.common.exception.response;

import com.example.picturediary.common.enums.ErrorCodes;

public class CustomErrorResponse
{
    private final int errorCode;
    private final String errorMessage;

    public CustomErrorResponse(ErrorCodes errorCodes)
    {
        this.errorCode = errorCodes.getErrorCode();
        this.errorMessage = errorCodes.getErrorMessage();
    }
}
