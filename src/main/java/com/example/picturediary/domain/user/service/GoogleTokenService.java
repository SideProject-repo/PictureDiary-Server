package com.example.picturediary.domain.user.service;

import com.example.picturediary.common.enums.ErrorCodes;
import com.example.picturediary.common.exception.customerror.CustomError;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class GoogleTokenService
{
    private static final String GOOGLE_URL = "https://oauth2.googleapis.com/tokeninfo?id_token=";

    private ObjectMapper objectMapper = new ObjectMapper();

    public String getUserIdFromSocialToken(String socialToken)
    {
        String response = getResponseFromKakao(socialToken);
        return getUserIdFromResponse(response);
    }

    private String getUserIdFromResponse(String response)
    {
        try {
            JsonNode jsonResponse = objectMapper.readTree(response);
            return jsonResponse.get("email").toString();
        }
        catch (Exception e) {
            throw new CustomError(ErrorCodes.JSON_PARSING_ERROR);
        }
    }

    private String getResponseFromKakao(String socialToken)
    {
        try {
            URL url = new URL(GOOGLE_URL + socialToken);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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
            throw new CustomError(ErrorCodes.GOOGLE_SERVER_ERROR);
        }
    }
}
