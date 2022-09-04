package com.example.picturediary.domain.user.controller;

import com.example.picturediary.common.exception.customerror.CustomError;
import com.example.picturediary.domain.user.entity.DiaryUser;
import com.example.picturediary.domain.user.repository.UserRepository;
import com.example.picturediary.security.PictureDiaryAuthenticationEntryPoint;
import com.example.picturediary.security.jwt.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private PictureDiaryAuthenticationEntryPoint pictureDiaryAuthenticationEntryPoint;

    @Test
    @DisplayName("회원 가입 url이 필터를 무시한다.")
    public void filterTest() throws Exception
    {
        mockMvc.perform(post("/dont-pass-filter"))
            .andExpect(status().isNotFound())
            .andDo(print());
    }

    @Nested
    @DisplayName("탈퇴 테스트")
    class leaveTest
    {
        @Test
        @DisplayName("탈퇴 성공")
        void leaveSuccess() throws Exception
        {
            // 테스트 용 회원 db 에 저장 및 access token 생성
            DiaryUser testDiaryUser = DiaryUser.builder()
                .socialId("social id")
                .lastAccessDateTime(LocalDateTime.now())
                .build();

            userRepository.save(testDiaryUser);

            String accessToken = JwtUtil.createAccessToken(testDiaryUser.getUserId());

            // 탈퇴
            mockMvc.perform(
                post("/auth/leave")
                    .header(HttpHeaders.AUTHORIZATION, accessToken)
            )
                .andExpect(status().isOk())
                .andDo(print());
        }

        @Test
        @DisplayName("존재하지 않는 회원 탈퇴 시도하면 에러 리턴")
        void leaveFail_NotExistUser() throws Exception
        {
            // 존재하지 않는 회원 번호
            String accessToken = JwtUtil.createAccessToken(0L);

            mockMvc.perform(
                post("/auth/leave")
                    .header(HttpHeaders.AUTHORIZATION, accessToken)
            )
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CustomError))
                .andDo(print());
        }
    }

    @Nested
    @DisplayName("access token 재발급 테스트")
    class Reissue
    {
        @Test
        @DisplayName("access token 재발급 성공")
        void reissueAccessTokenSuccess() throws Exception
        {
            String refreshToken = JwtUtil.createRefreshToken(1L);

            mockMvc.perform(
                post("/auth/reissue/access-token")
                    .param("refreshToken", refreshToken)
            )
                .andExpect(status().isOk())
                .andDo(print());
        }

        @Test
        @DisplayName("유효하지 않은 refresh 토큰으로 요청하면 에러 리턴")
        void reissueAccessTokenFail_InvalidRefreshToken() throws Exception
        {
            mockMvc.perform(
                post("/auth/reissue/access-token")
                    .param("refreshToken", "INVALID_REFRESH_TOKEN")
            )
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CustomError))
                .andDo(print());
        }
    }
}
