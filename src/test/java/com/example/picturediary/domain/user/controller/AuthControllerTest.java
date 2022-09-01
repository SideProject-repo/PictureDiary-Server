package com.example.picturediary.domain.user.controller;

import com.example.picturediary.security.PictureDiaryAuthenticationEntryPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest
{
    @Autowired
    private MockMvc mockMvc;

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
}
