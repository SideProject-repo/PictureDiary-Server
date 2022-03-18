package com.example.picturediary.domain.user.service;

import com.example.picturediary.common.enums.ErrorCodes;
import com.example.picturediary.common.exception.customerror.CustomError;
import com.example.picturediary.common.util.KakaoUtil;
import com.example.picturediary.domain.user.entity.DiaryUser;
import com.example.picturediary.domain.user.repository.UserRepository;
import com.example.picturediary.domain.user.request.SignInRequest;
import com.example.picturediary.domain.user.request.SignUpRequest;
import com.example.picturediary.domain.user.response.SignInResponse;
import com.example.picturediary.domain.user.response.SignUpResponse;
import com.example.picturediary.security.jwt.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService
{
    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @ApiOperation("카카오 회원가입")
    public SignUpResponse signUp(SignUpRequest signUpRequest)
    {
        Long socialId = KakaoUtil.getUserIdFromKakaoToken(signUpRequest.getSocialToken());

        if (!userRepository.existsBySocialId(socialId))
        {
            DiaryUser diaryUser = DiaryUser.builder()
                .socialId(socialId)
                .build();

            userRepository.save(diaryUser);

            String accessToken = JwtUtil.createAccessToken(socialId.toString());

            return SignUpResponse.builder()
                .accessToken(accessToken)
                .build();
        }
        else
        {
            throw new CustomError(ErrorCodes.ALREADY_SIGN_UP_USER);
        }
    }

    @ApiOperation("카카오 로그인")
    public SignInResponse signIn(SignInRequest signInRequest)
    {
        Long socialId = KakaoUtil.getUserIdFromKakaoToken(signInRequest.getSocialToken());

        if (userRepository.existsBySocialId(socialId))
        {
            String accessToken = JwtUtil.createAccessToken(socialId.toString());

            return SignInResponse.builder()
                .accessToken(accessToken)
                .build();
        }
        else
        {
            throw new CustomError(ErrorCodes.ALREADY_SIGN_UP_USER);
        }
    }

    public void logout()
    {
    }
}
