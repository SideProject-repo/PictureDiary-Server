package com.example.picturediary.domain.stamp.controller;

import com.example.picturediary.common.enums.StampType;
import com.example.picturediary.common.enums.Weather;
import com.example.picturediary.common.exception.customerror.CustomError;
import com.example.picturediary.common.util.StaticUtil;
import com.example.picturediary.domain.diary.entity.Diary;
import com.example.picturediary.domain.diary.repository.DiaryRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StampControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DiaryRepository diaryRepository;

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
    @DisplayName("도장 찍기 성공")
    void addStampSuccess() throws Exception
    {
        mockMvc.perform(
            post("/stamp")
                .header(HttpHeaders.AUTHORIZATION, StaticUtil.INFINITE_JWT_TOKEN)
                .param("diaryId", testDiary.getDiaryId().toString())
                .param("stampType", StampType.DOTHIS.name())
                .param("x", "0.1")
                .param("y", "0.1")
        )
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 diary Id 로 도장 찍기 요청하면 에러 리턴")
    void addStampFail_NotExistDiaryId() throws Exception
    {
        mockMvc.perform(
            post("/stamp")
                .header(HttpHeaders.AUTHORIZATION, StaticUtil.INFINITE_JWT_TOKEN)
                .param("diaryId", "0")      // 존재하지 않는 diary Id
                .param("stampType", StampType.DOTHIS.name())
                .param("x", "0.1")
                .param("y", "0.1")
        )
            .andExpect(status().isInternalServerError())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof CustomError))
            .andDo(print());
    }

    @Test
    @DisplayName("필수인 필드 null 일 경우 에러 발생")
    void addStampFail_NotNull() throws Exception
    {
        mockMvc.perform(
            post("/stamp")
                .header(HttpHeaders.AUTHORIZATION, StaticUtil.INFINITE_JWT_TOKEN)
                .param("stampType", StampType.DOTHIS.name())
                .param("x", "0.1")
                .param("y", "0.1")
            // 필수인 diary Id 필드 null 로 요청
        )
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @Test
    @DisplayName("유효하지 않은 StampType 일 경우 에러 발생")
    void addStampFail_InvalidStampType() throws Exception
    {
        mockMvc.perform(
            post("/stamp")
                .header(HttpHeaders.AUTHORIZATION, StaticUtil.INFINITE_JWT_TOKEN)
                .param("diaryId", "0")
                .param("stampType", "INVALID_STAMP_TYPE")       // 유효하지 않은 StampType
                .param("x", "0.1")
                .param("y", "0.1")
        )
            .andExpect(status().isBadRequest())
            .andDo(print());
    }
}
