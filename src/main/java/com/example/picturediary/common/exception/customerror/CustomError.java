package com.example.picturediary.common.exception.customerror;

import com.example.picturediary.common.enums.ErrorCodes;
import lombok.Getter;

@Getter
public class CustomError extends RuntimeException
{
    private final ErrorCodes errorCodes;

    public CustomError(ErrorCodes errorCodes)
    {
        this.errorCodes = errorCodes;
    }
}
