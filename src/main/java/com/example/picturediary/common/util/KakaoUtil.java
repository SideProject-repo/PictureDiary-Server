package com.example.picturediary.common.util;

import com.example.picturediary.common.enums.ErrorCodes;
import com.example.picturediary.common.exception.customerror.CustomError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@UtilityClass
public class KakaoUtil
{
    @Value("${spring.social.kakao-url}")
    private String kakaoUrl;

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static Long getUserIdFromKakaoToken(String socialToken)
    {
        String response = getResponseFromKakao(socialToken);
        return getUserIdFromResponse(response);
    }

    private static Long getUserIdFromResponse(String response)
    {
        try {
            JsonNode jsonResponse = objectMapper.readTree(response);
            return jsonResponse.get("id").asLong();
        }
        catch (Exception e) {
            throw new CustomError(ErrorCodes.JSON_PARSING_ERROR);
        }
    }

    private static String getResponseFromKakao(String socialToken)
    {
        try {
            URL url = new URL(kakaoUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = null;

            String temp;
            while ((temp = br.readLine()) != null)
            {
                response.append(temp);
            }

            return response.toString();
        }
        catch(Exception e) {
            throw new CustomError(ErrorCodes.KAKAO_SERVER_ERROR);
        }
    }
}
