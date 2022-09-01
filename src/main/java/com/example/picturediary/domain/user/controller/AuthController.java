package com.example.picturediary.domain.user.controller;

import com.example.picturediary.common.response.CommonResponse;
import com.example.picturediary.domain.user.request.SignInRequest;
import com.example.picturediary.domain.user.request.SignUpRequest;
import com.example.picturediary.domain.user.response.ReissueAccessTokenResponse;
import com.example.picturediary.domain.user.response.SignInResponse;
import com.example.picturediary.domain.user.response.SignUpResponse;
import com.example.picturediary.domain.user.service.AuthService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

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
    public SignUpResponse signUp(@Valid SignUpRequest signUpRequest)
    {
        return authService.signUp(signUpRequest);
    }

    @ApiOperation("로그인")
    @PostMapping("/sign-in")
    public SignInResponse signIn(@Valid SignInRequest signInRequest)
    {
        return authService.signIn(signInRequest);
    }

    @ApiOperation("탈퇴 - 로그인/회원가입시 발급한 JWT 토큰 필요")
    @PostMapping("/leave")
    public ResponseEntity<CommonResponse> leave(@AuthenticationPrincipal @ApiIgnore UserDetails user)
    {
        authService.leave(user);
        return new ResponseEntity("user id " + user.getUsername() + "님의 탈퇴 성공", HttpStatus.OK);
    }

    @ApiOperation("access token 재발급 - 그림 일기 refresh token 으로 요청")
    @PostMapping("/reissue/access-token")
    public ReissueAccessTokenResponse reissueAccessToken(String refreshToken)
    {
        return authService.reissueAccessToken(refreshToken);
    }
}
