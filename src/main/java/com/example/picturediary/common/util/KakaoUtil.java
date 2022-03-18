package com.example.picturediary.common.util;

import com.example.picturediary.common.enums.ErrorCodes;
import com.example.picturediary.common.exception.customerror.CustomError;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@UtilityClass
public class KakaoUtil
{
    private String kakaoUrl = "https://kapi.kakao.com/v1/user/access_token_info";

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

            connection.setRequestProperty(HttpHeaders.AUTHORIZATION, "Bearer " + socialToken);
            connection.setRequestProperty(HttpHeaders.CONTENT_TYPE, "application/json");
            connection.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();

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
