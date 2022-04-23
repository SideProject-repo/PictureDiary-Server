package com.example.picturediary.domain.diary.controller;

import com.example.picturediary.domain.diary.request.CreateDiaryRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class DiaryControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception
    {
        mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("일기 생성 성공")
    public void createDiarySuccess() throws Exception
    {
        CreateDiaryRequest request = CreateDiaryRequest.builder().build();
        String content = mapper.writeValueAsString(request);

        mockMvc.perform(
                post("/diary")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
            .andExpect(status().isOk())
            .andDo(print());
    }
}
