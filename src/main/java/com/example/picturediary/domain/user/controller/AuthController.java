package com.example.picturediary.domain.user.controller;

import com.example.picturediary.domain.user.request.SignInRequest;
import com.example.picturediary.domain.user.request.SignUpRequest;
import com.example.picturediary.domain.user.response.SignInResponse;
import com.example.picturediary.domain.user.response.SignUpResponse;
import com.example.picturediary.domain.user.service.AuthService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController
{
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService)
    {
        this.authService = authService;
    }

    @ApiOperation("회원 가입")
    @PostMapping("/sign-up")
    public SignUpResponse signUp(SignUpRequest signUpRequest)
    {
        return authService.signUp(signUpRequest);
    }

    @ApiOperation("로그인")
    @PostMapping("/sign-in")
    public SignInResponse signIn(SignInRequest signInRequest)
    {
        return authService.signIn(signInRequest);
    }

    @ApiOperation("탈퇴")
    @PostMapping("/leave")
    public void logout()
    {
        authService.leave();
    }

}
