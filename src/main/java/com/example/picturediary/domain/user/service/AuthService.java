package com.example.picturediary.domain.user.service;

import com.example.picturediary.common.enums.ErrorCodes;
import com.example.picturediary.common.enums.SocialType;
import com.example.picturediary.common.exception.customerror.CustomError;
import com.example.picturediary.domain.user.entity.DiaryUser;
import com.example.picturediary.domain.user.repository.UserRepository;
import com.example.picturediary.domain.user.request.SignInRequest;
import com.example.picturediary.domain.user.request.SignUpRequest;
import com.example.picturediary.domain.user.response.ReissueAccessTokenResponse;
import com.example.picturediary.domain.user.response.SignInResponse;
import com.example.picturediary.domain.user.response.SignUpResponse;
import com.example.picturediary.security.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

@Transactional
@Service
public class AuthService
{
    private final UserRepository userRepository;
    private final KakaoTokenService kakaoTokenService;
    private final GoogleTokenService googleTokenService;
    private final AppleTokenService appleTokenService;

    @Autowired
    public AuthService(
        UserRepository userRepository,
        KakaoTokenService kakaoTokenService,
        GoogleTokenService googleTokenService,
        AppleTokenService appleTokenService)
    {
        this.userRepository = userRepository;
        this.kakaoTokenService = kakaoTokenService;
        this.googleTokenService = googleTokenService;
        this.appleTokenService = appleTokenService;
    }

    public SignUpResponse signUp(SignUpRequest signUpRequest)
    {
        String socialId = getUserIdFromSocialToken(signUpRequest.getSocialType(), signUpRequest.getSocialToken());

        if (!userRepository.existsBySocialId(socialId))
        {
            DiaryUser diaryUser = DiaryUser.of(signUpRequest, socialId);

            DiaryUser user = userRepository.save(diaryUser);

            String accessToken = JwtUtil.createAccessToken(user.getUserId());
            String refreshToken = JwtUtil.createRefreshToken(user.getUserId());

            return SignUpResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        }
        else
        {
            throw new CustomError(ErrorCodes.ALREADY_SIGN_UP_USER);
        }
    }

    public SignInResponse signIn(SignInRequest signInRequest)
    {
        String socialId = getUserIdFromSocialToken(signInRequest.getSocialType(), signInRequest.getSocialToken());

        DiaryUser user = userRepository.getDiaryUserBySocialId(socialId);

        if (!ObjectUtils.isEmpty(user))
        {
            user.updateLastAccessDateTime();

            String accessToken = JwtUtil.createAccessToken(user.getUserId());
            String refreshToken = JwtUtil.createRefreshToken(user.getUserId());

            return SignInResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        }
        else
        {
            throw new CustomError(ErrorCodes.NOT_SIGN_UP_USER);
        }
    }

    private String getUserIdFromSocialToken(String socialType, String socialToken)
    {
        if (StringUtils.equals(socialType, SocialType.KAKAO.getSocialTypeName()))
            return kakaoTokenService.getUserIdFromSocialToken(socialToken);
        else if (StringUtils.equals(socialType, SocialType.GOOGLE.getSocialTypeName()))
            return googleTokenService.getUserIdFromSocialToken(socialToken);
        else if (StringUtils.equals(socialType, SocialType.APPLE.getSocialTypeName()))
            return appleTokenService.getUserIdFromSocialToken(socialToken);
        else
            throw new CustomError(ErrorCodes.NOT_EXIST_SOCIAL_TYPE_ERROR);
    }

    public void leave(UserDetails user)
    {
        if (!userRepository.existsById(Long.valueOf(user.getUsername())))
            throw new CustomError(ErrorCodes.NOT_EXIST_USER);

        userRepository.deleteByUserId(Long.parseLong(user.getUsername()));
    }

    public ReissueAccessTokenResponse reissueAccessToken(String refreshToken)
    {
        try {
            Claims claims = JwtUtil.parseToken(refreshToken);
            String userId = JwtUtil.getUserDetailsFromClaims(claims).getUsername();

            String accessToken = JwtUtil.createAccessToken(Long.parseLong(userId));

            return ReissueAccessTokenResponse.builder()
                .accessToken(accessToken)
                .build();
        }
        catch (Exception e)
        {
            throw new CustomError(ErrorCodes.INVALID_REFRESH_TOKEN_ERROR);
        }
    }
}
