package com.example.picturediary.common.exception.controller;

import com.example.picturediary.common.exception.customerror.CustomError;
import com.example.picturediary.common.exception.response.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController
{
    @ExceptionHandler(CustomError.class)
    public ResponseEntity<CustomErrorResponse> handle(CustomError e)
    {
        return new ResponseEntity<>(new CustomErrorResponse(e.getErrorCodes()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
