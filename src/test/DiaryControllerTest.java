package com.example.picturediary.domain.diary.controller;

import com.example.picturediary.IntegrationTest;
import com.example.picturediary.common.enums.Weather;
import com.example.picturediary.common.util.StaticUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DiaryControllerTest extends IntegrationTest
{
    @Nested
    @DisplayName("일기 생성 테스트")
    class createDiaryTest
    {
        @Test
        @DisplayName("일기 생성 성공")
        public void createDiarySuccess() throws Exception
        {
            mockMvc.perform(
                post("/diary")
                    .header(HttpHeaders.AUTHORIZATION, StaticUtil.INFINITE_JWT_TOKEN)
                    .param("weather", Weather.SUN.name())
                    .param("imageUrl", "imageUrl")
                    .param("content", "content")
            )
                .andExpect(status().isCreated())
                .andDo(print());
        }

        @Test
        @DisplayName("필수인 필드 null 일 경우 일기 생성 실패")
        public void createDiaryFailIfRequiredFieldNull() throws Exception
        {
            mockMvc.perform(
                post("/diary")
                    .header(HttpHeaders.AUTHORIZATION, StaticUtil.INFINITE_JWT_TOKEN)
                    .param("weather", Weather.SUN.name())
                    .param("content", "content")
                // 필수인 imageUrl 필드 넘겨주지 않음
            )
                .andExpect(status().isBadRequest())
                .andDo(print());
        }

        @Test
        @DisplayName("유효하지 않은 필드일 경우 일기 생성 실패")
        public void createDiaryFailIfInvalidField() throws Exception
        {
            mockMvc.perform(
                post("/diary")
                    .header(HttpHeaders.AUTHORIZATION, StaticUtil.INFINITE_JWT_TOKEN)
                    .param("weather", "INVALID_FIELD")      // 유효하지 않은 필드
                    .param("imageUrl", "imageUrl")
                    .param("content", "content")
            )
                .andExpect(status().isBadRequest())
                .andDo(print());
        }
    }

    @Nested
    @DisplayName("일기 list 조회 테스트")
    class getDiaryListTest
    {
        @Test
        @DisplayName("일기 list 조회 성공")
        void getDiaryListSuccess() throws Exception
        {
            mockMvc.perform(
                get("/diary/list")
                    .header(HttpHeaders.AUTHORIZATION, StaticUtil.INFINITE_JWT_TOKEN)
                    .param("lastDiaryId", "1")
                    .param("size", "1")
            )
                .andExpect(status().isOk())
                .andDo(print());
        }
    }

     @Nested
     @DisplayName("일기 단건 조회 테스트")
     class getSingleDiaryTest
     {
         @Test
         @DisplayName("일기 단건 조회 성공")
         void getSingleDiarySuccess() throws Exception
         {
             mockMvc.perform(
                 get("/diary/1")
                     .header(HttpHeaders.AUTHORIZATION, StaticUtil.INFINITE_JWT_TOKEN)
             )
                 .andExpect(status().isOk())
                 .andDo(print());
         }
     }
}
