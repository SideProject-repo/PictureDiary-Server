package com.example.picturediary.domain.diary.controller;

import com.example.picturediary.common.enums.Weather;
import com.example.picturediary.common.exception.customerror.CustomError;
import com.example.picturediary.common.util.StaticUtil;
import com.example.picturediary.domain.diary.entity.Diary;
import com.example.picturediary.domain.diary.repository.DiaryRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class DiaryControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DiaryRepository diaryRepository;

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
     @DisplayName("일기 조회 테스트")
     @TestInstance(TestInstance.Lifecycle.PER_CLASS)
     class getDiaryTest
     {
         private Diary testDiary;

         @BeforeAll
         void setUp()
         {
             // 테스트용 데이터 추가
             testDiary = Diary.builder()
                 .userId(1L)
                 .imageUrl("image-url")
                 .weather(Weather.CLOUD)
                 .content("content")
                 .build();

             diaryRepository.save(testDiary);
         }

         @AfterAll
         void tearDown()
         {
             // 테스트용 데이터 삭제
             diaryRepository.deleteById(testDiary.getDiaryId());
         }

         @Test
         @DisplayName("일기 단건 조회 성공")
         void getSingleDiarySuccess() throws Exception
         {
             mockMvc.perform(
                 get("/diary/" + testDiary.getDiaryId())
                     .header(HttpHeaders.AUTHORIZATION, StaticUtil.INFINITE_JWT_TOKEN)
             )
                 .andExpect(status().isOk())
                 .andDo(print());
         }

         @Test
         @DisplayName("존재하지 않는 diary Id로 일기 단건 조회 시도하면 에러 리턴")
         void getSingleDiaryFailNotExistDiaryId() throws Exception
         {
             mockMvc.perform(
                 get("/diary/0")
                     .header(HttpHeaders.AUTHORIZATION, StaticUtil.INFINITE_JWT_TOKEN)
             )
                 .andExpect(status().isInternalServerError())
                 .andExpect(result -> assertTrue(result.getResolvedException() instanceof CustomError))
                 .andDo(print());
         }

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
}
